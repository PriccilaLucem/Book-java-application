package com.example.java_application.util;


import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class Mapper {
    public static <S,D> D parseObject(S source, Class<D> Destination ){   
        ModelMapper modelMapper = new ModelMapper();
        D destionation = modelMapper.map(source,Destination);
        return destionation;
    }
    public static <S,D> List<D> parseObjectList(List<S> sourceList, Class<D> destination){
        ModelMapper modelMapper = new ModelMapper();
        return sourceList.stream().map(source -> modelMapper.map(source, destination)).collect(Collectors.toList());

    }

}
