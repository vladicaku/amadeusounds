//package com.amadeusounds.config;
//
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationConfig;
//import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
//import org.springframework.context.annotation.*;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.util.List;
//
///**
// * Created by Vac on 4/13/2016.
// */
//@org.springframework.context.annotation.Configuration
//class WebConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper mapper = new ObjectMapper() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
//                // replace the configuration with my modified configuration.
//                // calling "withView" should keep previous config and just add my changes.
//                //return super._serializerProvider(config.withView(Views.Public.class));
//            }
//        };
//        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//        converter.setObjectMapper(mapper);
//        converters.add(converter);
//    }
//}
