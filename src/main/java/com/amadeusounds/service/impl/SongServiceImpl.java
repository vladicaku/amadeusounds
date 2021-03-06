package com.amadeusounds.service.impl;

import com.amadeusounds.model.Rating;
import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.BaseRepository;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.service.SongService;
import com.amadeusounds.util.SpecificationUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vac on 4/13/2016.
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    Environment environment;

    @Autowired
    SongRepository songRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    BaseRepository baseRepository;


    @Override
    public Song findSongById(Long id) {
        return songRepository.findOne(id);
    }

    @Override
    @Transactional
    public Song saveSong(Song song) {
        if (song.getDate() == null) {
            song.setDate(LocalDate.now());
        }

        if (song.getSong() == null) {
            Blob songBlob = Hibernate.getLobCreator(getCurrentSession()).createBlob(new byte[0]);
            song.setSong(songBlob);
        }

        return songRepository.saveAndFlush(song);
    }

    @Override
    @Transactional
    public Song updateSong(Song song) {
        return songRepository.saveAndFlush(song);
    }

    @Override
    @Transactional
    public void deleteSong(Song song) {
        songRepository.delete(song);
    }

    @Override
    @Transactional
    public void deleteSong(Long songId) {
        songRepository.delete(songId);
    }

    @Override
    @Transactional
    public void addBlobToSong(Song song, MultipartFile multipartFile) throws Exception {
        Blob songBlob = Hibernate.getLobCreator(getCurrentSession()).createBlob(multipartFile.getInputStream(), multipartFile.getSize());
        if (song.getSong().length() != 0) {
            throw new Exception("Cannot change the song content once it is uploaded.");
        }
        song.setSong(songBlob);
        songRepository.saveAndFlush(song);
    }

    @Override
    public Page<Song> getAllSongsForUser(User user, Pageable pageable) {
        return songRepository.findAll(SpecificationUtils.<Song>equals("user", user), pageable);
    }

    @Override
    public Page<Song> getLatestSongs(Pageable pageable) {
        // the query
        int days = Integer.valueOf(environment.getProperty("amadeusounds.songs.latest.days", "3"));
        int max = Integer.valueOf(environment.getProperty("amadeusounds.songs.latest.max-results", "30"));
        LocalDate date = LocalDate.now().minusDays(days);
        Specifications specifications = Specifications.where(SpecificationUtils.<Song>greaterThan("date", date, true));

        // get total
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Song> criteriaQuery = criteriaBuilder.createQuery(Song.class);
        Root<Song> root = criteriaQuery.from(Song.class);
        criteriaQuery.select(root);
        criteriaQuery.where(specifications.toPredicate(root, criteriaQuery, criteriaBuilder));
        long total = entityManager.createQuery(criteriaQuery).setMaxResults(max).getResultList().size();

        // get results
        CriteriaQuery<Song> criteriaQuery1 = criteriaBuilder.createQuery(Song.class);
        Root<Song> root1 = criteriaQuery1.from(Song.class);
        criteriaQuery1.select(root1);
        criteriaQuery1.where(specifications.toPredicate(root1, criteriaQuery1, criteriaBuilder));
        criteriaQuery1.orderBy(criteriaBuilder.desc(root1.get("date")));

        // check out of bound
        boolean executeQuery = true;
        int queryOffset = pageable.getPageNumber() * pageable.getPageSize();
        executeQuery = (total - queryOffset) <= 0 ? false : true;
        int queryMax = total - queryOffset < pageable.getPageSize() ? (int) (total - queryOffset) : pageable.getPageSize();

        List<Song> content = null;
        if (executeQuery) {
            content = entityManager.createQuery(criteriaQuery1).setFirstResult(queryOffset).setMaxResults(queryMax).getResultList();
        }
        else {
            content = new ArrayList<>(0);
        }

        Page<Song> page = new PageImpl<Song>(content, pageable, total);
        return page;
    }

    @Override
    public Page<Song> getTrendingSongs(Pageable pageable) {
        return null;
    }

    /**
     * TODO:
     * Da se razgleda dali presmetuvanjeto na rejtinzi da bide dinamicko
     * ili pri sekoj nov rejt da se zapise i presmeta vo bazata
     */
    @Override
    public Page<Song> getTopRatedSongs(Pageable pageable) {
        int max = Integer.valueOf(environment.getProperty("amadeusounds.songs.top-rated.max-results", "30"));

        // get total
        CriteriaBuilder criteriaBuilderTotal = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQueryTotal = criteriaBuilderTotal.createQuery(Long.class);
        Root<Song> rootTotal = criteriaQueryTotal.from(Song.class);
        criteriaQueryTotal.select(criteriaBuilderTotal.count(rootTotal));
        long total = entityManager.createQuery(criteriaQueryTotal).getSingleResult();
        total = total > max ? max : total;

        // the query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root<Song> root = criteriaQuery.from(Song.class);
        Join<Song, Rating> join = root.join("ratings");

        Expression<Long> sum = criteriaBuilder.sum(join.get("rating"));
        Expression<Long> count = criteriaBuilder.count(join);
        criteriaQuery.multiselect(root, sum, count);
        criteriaQuery.orderBy(criteriaBuilder.desc(sum));
        criteriaQuery.groupBy(root);

        // check out of bound
        boolean executeQuery = true;
        int queryOffset = pageable.getPageNumber() * pageable.getPageSize();
        executeQuery = (total - queryOffset) <= 0 ? false : true;
        int queryMax = total - queryOffset < pageable.getPageSize() ? (int) (total - queryOffset) : pageable.getPageSize();

        List<Object> content = null;
        if (executeQuery) {
            content = entityManager.createQuery(criteriaQuery).setFirstResult(queryOffset).setMaxResults(queryMax).getResultList();
        }
        else {
            content = new ArrayList<>(0);
        }

        List<Song> songList = new ArrayList<>(content.size());
        for (Object obj : content) {
            //BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            //BeanWrapperImpl wrapper = new BeanWrapperImpl(obj);
            Song song = (Song) ((Object[]) obj)[0];
            long ratingSum = (Long) ((Object[]) obj)[1];
            long ratingCount = (Long) ((Object[]) obj)[2];
            song.setRating(ratingSum * 1.0 / ratingCount);
            songList.add(song);
        }

        Page<Song> page = new PageImpl<Song>(songList, pageable, total);
        return page;
    }

    @Override
    public Page<Song> findAllSongs(String term, Pageable pageable) {
        return null;
    }

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }
}
