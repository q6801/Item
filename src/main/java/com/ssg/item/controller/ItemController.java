package com.ssg.item.controller;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import com.ssg.item.dto.ItemDto;
import com.ssg.item.dto.ItemResDto;
import com.ssg.item.dto.UserDto;
import com.ssg.item.dto.UserResDto;
import com.ssg.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("v1")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("item")
    public ApiResult<ItemResDto> setItem(@Valid @RequestBody ItemDto itemDto) {
        ItemResDto itemResDto = itemService.setItem(itemDto);
        return ApiProvider.success(itemResDto);
    }

    @DeleteMapping("item/{itemId}")
    public ApiResult<?> deleteItem(@PathVariable long itemId) {
        itemService.deleteItem(itemId);
        return ApiProvider.success();
    }
}
