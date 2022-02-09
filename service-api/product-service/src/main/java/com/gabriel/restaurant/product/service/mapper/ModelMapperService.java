package com.gabriel.restaurant.product.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelMapperService {

    @Autowired
    private ModelMapper modelMapper;

    public <D, E> D convert(E element, Class<D> destinationType) {
        return modelMapper.map(element, destinationType);
    }

    public <D, E> List<D> convert(List<E> elements, Class<D> destinationType) {
        List<D> list = new ArrayList<>(elements.size());
        elements.forEach(element -> list.add(convert(element, destinationType)));
        return list;
    }
}
