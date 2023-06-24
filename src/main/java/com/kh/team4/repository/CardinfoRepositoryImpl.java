package com.kh.team4.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.team4.entity.Cardinfo;
import com.kh.team4.entity.QCardinfo;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class CardinfoRepositoryImpl implements CardinfoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public CardinfoRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public String top1(String maxvalueColumnName, Long cardno) {
        QCardinfo cardinfo = QCardinfo.cardinfo;
        PathBuilder<Cardinfo> pathBuilder = new PathBuilder<>(Cardinfo.class, cardinfo.getMetadata());
        StringPath columnPath = pathBuilder.getString(maxvalueColumnName);
        Optional<String> result = Optional.ofNullable(jpaQueryFactory
                        .select(columnPath)
                        .from(cardinfo)
                        .where(cardinfo.cardno.eq(cardno).and(columnPath.isNotNull()).and(columnPath.isNotEmpty()))
                        .fetchFirst())
                .filter(value -> !value.isEmpty());
        String extractedValue = result
                .map(value -> value.replaceAll("^Optional\\[|\\]$", "0"))
                .orElse("0");

        return extractedValue;
    }


    public String top2(String midvalueColumnName, Long cardno) {
        QCardinfo cardinfo = QCardinfo.cardinfo;
        PathBuilder<Cardinfo> pathBuilder = new PathBuilder<>(Cardinfo.class, cardinfo.getMetadata());
        StringPath columnPath = pathBuilder.getString(midvalueColumnName);
        Optional<String> result = Optional.ofNullable(jpaQueryFactory
                        .select(columnPath)
                        .from(cardinfo)
                        .where(cardinfo.cardno.eq(cardno).and(columnPath.isNotNull()).and(columnPath.isNotEmpty()))
                        .fetchFirst())
                .filter(value -> !value.isEmpty());
        String extractedValue = result
                .map(value -> value.replaceAll("^Optional\\[|\\]$", ""))
                .orElse("0");

        return extractedValue;
    }
    public String top3(String thrdvalueColumnName, Long cardno) {
        QCardinfo cardinfo = QCardinfo.cardinfo;
        PathBuilder<Cardinfo> pathBuilder = new PathBuilder<>(Cardinfo.class, cardinfo.getMetadata());
        StringPath columnPath = pathBuilder.getString(thrdvalueColumnName);
        Optional<String> result = Optional.ofNullable(jpaQueryFactory
                        .select(columnPath)
                        .from(cardinfo)
                        .where(cardinfo.cardno.eq(cardno).and(columnPath.isNotNull()).and(columnPath.isNotEmpty()))
                        .fetchFirst())
                .filter(value -> !value.isEmpty());
        String extractedValue = result
                .map(value -> value.replaceAll("^Optional\\[|\\]$", ""))
                .orElse("0");
        return extractedValue;
    }

    @Override
    public String findByCno(Long cardno) {
        QCardinfo cardinfo = QCardinfo.cardinfo;
        NumberPath<Long> columnPath = cardinfo.cardno;

        Optional<String> result = Optional.ofNullable(String.valueOf(jpaQueryFactory
                .select(cardinfo)
                .from(cardinfo)
                .where(columnPath.eq(cardno))
                .fetchFirst()));
        String extractedValue = result
                .map(value -> value.replaceAll("^Optional\\[|\\]$", ""))
                .orElse("0");
        return extractedValue;
    }
}
