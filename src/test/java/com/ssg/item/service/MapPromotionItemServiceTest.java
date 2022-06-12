package com.ssg.item.service;

import com.ssg.item.dto.MapPromotionItemDto;
import com.ssg.item.entity.Item;
import com.ssg.item.entity.Promotion;
import com.ssg.item.enums.ItemType;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.repository.ItemRepository;
import com.ssg.item.repository.MapPromotionItemRepository;
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
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MapPromotionItemServiceTest {
    private static Validator validator;

    @InjectMocks
    private MapPromotionItemServiceImpl promotionItemService;

    @Mock
    private PromotionService promotionSerice;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private MapPromotionItemRepository mapPromotionItemRepository;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("setPromotionItem 성공")
    public void setPromotionItem() {
        Promotion inputPromotion = getStubPromotions().get(0);
        Item inputItem0 = getStubItems().get(0);
        Item inputItem1 = getStubItems().get(1);
        MapPromotionItemDto mapPromotionItemDto = new MapPromotionItemDto(
                inputPromotion.getId(), inputItem0.getId() + "," + inputItem1.getId());

        given(promotionSerice.findById(inputPromotion.getId())).willReturn(inputPromotion);
        given(itemRepository.findById(inputItem0.getId())).willReturn(Optional.of(inputItem0));
        given(itemRepository.findById(inputItem1.getId())).willReturn(Optional.of(inputItem1));
        given(mapPromotionItemRepository.findByItemAndPromotion(inputItem0, inputPromotion)).willReturn(Optional.empty());
        given(mapPromotionItemRepository.findByItemAndPromotion(inputItem1, inputPromotion)).willReturn(Optional.empty());

        promotionItemService.setPromotionItem(mapPromotionItemDto);
    }

    @Test
    @DisplayName("해당하는 item이 없는 경우")
    public void setPromotionItemWithWrongItem() {
        Promotion inputPromotion = getStubPromotions().get(0);
        Item inputItem0 = getStubItems().get(0);
        Item inputItem1 = getStubItems().get(1);
        MapPromotionItemDto mapPromotionItemDto = new MapPromotionItemDto(
                inputPromotion.getId(), inputItem0.getId() + "," + inputItem1.getId());

        given(promotionSerice.findById(inputPromotion.getId())).willReturn(inputPromotion);
        given(itemRepository.findById(inputItem0.getId())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> promotionItemService.setPromotionItem(mapPromotionItemDto))
                .isInstanceOf(CustomRuntimeException.class);
    }

    @Test
    @DisplayName("item id로 숫자가 아닌 문자가 들어가는 경우")
    public void setPromotionItemWithString() {
        Promotion inputPromotion = getStubPromotions().get(0);
        Item inputItem0 = getStubItems().get(0);
        MapPromotionItemDto mapPromotionItemDto = new MapPromotionItemDto(
                inputPromotion.getId(), "hi," + inputItem0.getId());

        given(promotionSerice.findById(inputPromotion.getId())).willReturn(inputPromotion);

        Assertions.assertThatThrownBy(() -> promotionItemService.setPromotionItem(mapPromotionItemDto))
                .isInstanceOf(CustomRuntimeException.class);
    }

    @Test
    @DisplayName("PromotionItemDto의 itemIds에 빈 값 입력")
    public void setPromotionItemDto() {
        MapPromotionItemDto mapPromotionItemDto = new MapPromotionItemDto(1L, "");
        Set<ConstraintViolation<MapPromotionItemDto>> validate = validator.validate(mapPromotionItemDto);
        assertThat(validate.size()).isEqualTo(1);

        for(ConstraintViolation<MapPromotionItemDto> constraintViolation : validate){
            System.out.println(constraintViolation.getMessage());
        }
    }



    private List<Promotion> getStubPromotions() {
        Timestamp time0 = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Timestamp time1 = Timestamp.valueOf(LocalDateTime.now().plusMonths(1).withNano(0));
        Timestamp time2 = Timestamp.valueOf(LocalDateTime.now().plusMonths(2).withNano(0));
        Timestamp time3 = Timestamp.valueOf(LocalDateTime.now().plusMonths(2).withNano(0));

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion(0, "name0", null, 0.05f, time0, time1));
        promotions.add(new Promotion(1, "name1", 1000, 0.5f, time2, time3));
        return promotions;
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
