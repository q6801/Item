package com.ssg.item.service;

import com.ssg.item.dto.ItemDto;
import com.ssg.item.dto.ItemResDto;
import com.ssg.item.entity.Item;
import com.ssg.item.enums.ItemType;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    private static Validator validator;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("setItem 성공")
    public void setItem() {
        Item item = getStubItems().get(0);
        ItemDto inputData = new ItemDto(item.getName(), item.getItemType(), item.getItemPrice(),
                item.getItemDisplayStartDate(), item.getItemDisplayEndDate());

        given(itemRepository.save(item)).willReturn(item);
        ItemResDto itemResDto = itemService.setItem(inputData);

        assertThat(itemResDto.getName()).isEqualTo(item.getName());
        assertThat(itemResDto.getItemType()).isEqualTo(item.getItemType());
        assertThat(itemResDto.getItemPrice()).isEqualTo(item.getItemPrice());
        assertThat(itemResDto.getItemDisplayEndDate()).isEqualTo(item.getItemDisplayEndDate());
        assertThat(itemResDto.getItemDisplayStartDate()).isEqualTo(item.getItemDisplayStartDate());
    }

    @Test
    @DisplayName("item의 display 시작시간이 종료시간보다 늦거나 같은 경우")
    public void setItemWithWrongDate() {
        String itemName = "item name";
        ItemType itemType = ItemType.NORMAL_ITEM;
        int itemPrice = 100;
        Timestamp first = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Timestamp second = Timestamp.valueOf(LocalDateTime.now().plusMonths(1).withNano(0));

        Assertions.assertThatThrownBy(() -> new Item(itemName, itemType, itemPrice, second, first))
                .isInstanceOf(CustomRuntimeException.class);

        Assertions.assertThatThrownBy(() -> new ItemDto(itemName, itemType, itemPrice, second, first))
                .isInstanceOf(CustomRuntimeException.class);

        new ItemDto(itemName, itemType, itemPrice, first, second);
        new ItemDto(itemName, itemType, itemPrice, first, first);
        new ItemDto(itemName, itemType, itemPrice, first, second);
        new ItemDto(itemName, itemType, itemPrice, first, first);
    }

    @Test
    @DisplayName("itemDto에 잘못된 입력이 들어간 경우")
    public void setItemWithWrongInput() {
        ItemDto itemDto = new ItemDto("", null, -123, null, null);

        Set<ConstraintViolation<ItemDto>> validate = validator.validate(itemDto);
        assertThat(validate.size()).isEqualTo(5);
        for(ConstraintViolation<ItemDto> constraintViolation : validate){
            System.out.println(constraintViolation.getMessage());
        }
    }

    private List<Item> getStubItems() {
        Timestamp time0 = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Timestamp time1 = Timestamp.valueOf(LocalDateTime.now().plusMonths(1).withNano(0));
        Timestamp time2 = Timestamp.valueOf(LocalDateTime.now().plusMonths(2).withNano(0));
        Timestamp time3 = Timestamp.valueOf(LocalDateTime.now().plusMonths(2).withNano(0));

        List<Item> items = new ArrayList<>();
        items.add(new Item(0, "name0", ItemType.NORMAL_ITEM, 100, time0, time1));
        items.add(new Item(1, "name1", ItemType.CORPORATE_USER_ITEM, 200, time2, time3));
        return items;
    }
}
