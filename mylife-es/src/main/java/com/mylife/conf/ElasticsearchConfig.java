package com.mylife.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
/**
 * @descirption : es配置类
 * @author : wyh
 * @date : 2020/10/7 22:54
 */
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfigurationSupport {

    /**
     * 实体类映射
     * @param elasticsearchMappingContext
     * @return
     */
    @Override
    @Bean
    public ElasticsearchConverter elasticsearchEntityMapper(SimpleElasticsearchMappingContext elasticsearchMappingContext) {
        MappingElasticsearchConverter elasticsearchConverter = new MappingElasticsearchConverter(elasticsearchMappingContext);
        elasticsearchConverter.setConversions(this.elasticsearchCustomConversions());
        return elasticsearchConverter;
    }

    /**
     * 自定义转换器
     * @return
     */
    @Override
    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        List<Converter> converters= new ArrayList<>();
        converters.add(LongToLocalDateTimeConverter.INSTANCE);
        return new ElasticsearchCustomConversions(converters);
    }

    /**
     * 读取数据转换器：long to LocalDateTime
     */
    @ReadingConverter
    enum LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
        INSTANCE;

        @Override
        public java.time.LocalDateTime convert(Long source) {
            return Instant.ofEpochMilli(source).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }

}
