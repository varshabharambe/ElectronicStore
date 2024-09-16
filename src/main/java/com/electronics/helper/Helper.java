package com.electronics.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.electronics.dto.PageableResponse;
import com.electronics.dto.UserDto;
import com.electronics.model.User;

public class Helper {

	public static <V, U> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> dtoClass){
		List<U> listOfEntity = page.getContent();
		List<V> dtoList = listOfEntity.stream()
				.map(entity -> new ModelMapper().map(entity,dtoClass))
				.collect(Collectors.toList());
		PageableResponse<V> response = new PageableResponse<>();
		response.setContent(dtoList);
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setPageSize(page.getSize());
		response.setPageNumber(page.getNumber());
		response.setLastPage(page.isLast());
		return response;
	}
}
