package com.amadeusounds.service.impl;

import com.amadeusounds.model.Song;
import com.amadeusounds.model.User;
import com.amadeusounds.repository.BaseRepository;
import com.amadeusounds.repository.SongRepository;
import com.amadeusounds.service.SongService;
import com.amadeusounds.util.SpecificationUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.omg.CORBA.Object;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
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
    public void saveSong(Song song) {
        if (song.getDate() == null) {
            song.setDate(LocalDate.now());
        }

        if (song.getSong() == null) {
            Blob songBlob = Hibernate.getLobCreator(getCurrentSession()).createBlob(new byte[0]);
            song.setSong(songBlob);
        }

        songRepository.saveAndFlush(song);
    }

    @Override
    @Transactional
    public void updateSong(Song song) {
        songRepository.saveAndFlush(song);
    }

    @Override
    @Transactional
    public void deleteSong(Song song) {
        songRepository.delete(song);
    }

    @Override
    @Transactional
    public void addBlobToSong(Song song, MultipartFile multipartFile) throws IOException {
        Blob songBlob = Hibernate.getLobCreator(getCurrentSession()).createBlob(multipartFile.getInputStream(), multipartFile.getSize());
        song.setSong(songBlob);
        songRepository.saveAndFlush(song);
    }

    @Override
    public Page<Song> getAllSongsForUser(User user, Pageable pageable) {
        return null;
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
        int queryOffset = (pageable.getPageNumber()) * pageable.getPageSize();
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

    @Override
    public Page<Song> getTopRatedSongs(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Song> findAllSongs(String term, Pageable pageable) {
        return null;
    }


    public Page<Song> getSongsForUser(User user) {
        return null;
    }

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }
}
