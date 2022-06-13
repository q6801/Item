package com.ssg.item.service;

import com.ssg.item.dto.ItemDto;
import com.ssg.item.dto.ItemResDto;
import com.ssg.item.dto.ItemWithPromotionDto;
import com.ssg.item.entity.Item;
import com.ssg.item.entity.MapPromotionItem;
import com.ssg.item.entity.Promotion;
import com.ssg.item.entity.User;
import com.ssg.item.enums.ItemType;
import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    private static Validator validator;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserService userService;

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

    @Test
    @DisplayName("get buyable item 성공")
    public void getBuyableItem() {
        User normalUser = getStubUsers().get(0);
        User withdrawalUser = getStubUsers().get(1);
        User corporateUser = getStubUsers().get(2);
        given(userService.findById(normalUser.getId())).willReturn(normalUser);
        given(userService.findById(withdrawalUser.getId())).willReturn(withdrawalUser);
        given(userService.findById(corporateUser.getId())).willReturn(corporateUser);
        given(itemRepository.findBuyableItem(ItemType.NORMAL_ITEM))
                .willReturn(getStubItems().stream().filter(i -> i.getItemType().equals(ItemType.NORMAL_ITEM)).collect(Collectors.toList()));
        given(itemRepository.findBuyableItem())
                .willReturn(getStubItems());

        List<ItemResDto> buyableItems0 = itemService.getBuyableItem(normalUser.getId());
        List<ItemResDto> buyableItems1 = itemService.getBuyableItem(withdrawalUser.getId());
        List<ItemResDto> buyableItems2 = itemService.getBuyableItem(corporateUser.getId());


        assertThat(buyableItems0.size()).isEqualTo(1);
        assertThat(buyableItems1.size()).isEqualTo(0);
        assertThat(buyableItems2.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품과 그에 해당하는 프로모션 조회 성공")
    public void getItemWithPromotion() {
        Item item = getStubItems().get(0);
        int itemPrice = item.getItemPrice();
        Promotion promotion = getStubPromotions().get(0);
        setMapPromotionItem(item, promotion);

        int discountAmount = promotion.getDiscountAmount() != null ? promotion.getDiscountAmount(): 0;
        float discountRate = promotion.getDiscountRate() != null ? promotion.getDiscountRate(): 0;
        int price0 = itemPrice - discountAmount;
        int price1 = (int) (itemPrice * (1 - discountRate));
        price0 = price0 > 0 ? price0 : itemPrice;
        price1 = price1 > 0 ? price1 : itemPrice;

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));

        ItemWithPromotionDto itemWithPromotion = itemService.getItemWithPromotion(item.getId());
        assertThat(itemWithPromotion.getPromotionResDto()).isEqualTo(promotion.convertPromotionToResDto());
        assertThat(itemWithPromotion.getItemDiscountDto().getDiscountedPrice()).isEqualTo(Math.min(price0, price1));
    }

    @Test
    @DisplayName("상품과 그에 해당하는 프로모션 조회 (프로모션이 없는 경우")
    public void getItemWithNullPromotion() {
        Item item = getStubItems().get(0);
        int itemPrice = item.getItemPrice();

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));

        ItemWithPromotionDto itemWithPromotion = itemService.getItemWithPromotion(item.getId());
        assertThat(itemWithPromotion.getItemDiscountDto().getDiscountedPrice()).isEqualTo(itemPrice);
    }

    @Test
    @DisplayName("상품과 그에 해당하는 프로모션 조회 (프로모션 할인이 너무 커 상품 가격이 0보다 적어질 때, 할인 적용 x)")
    public void getItemWithTooMuchDiscount() {
        Item item = getStubItems().get(0);
        int itemPrice = item.getItemPrice();
        Promotion promotion = getStubPromotions().get(2);
        setMapPromotionItem(item, promotion);

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));

        ItemWithPromotionDto itemWithPromotion = itemService.getItemWithPromotion(item.getId());
        assertThat(itemWithPromotion.getPromotionResDto()).isEqualTo(null);
        assertThat(itemWithPromotion.getItemDiscountDto().getDiscountedPrice()).isEqualTo(itemPrice);
    }

    @Test
    @DisplayName("상품과 그에 해당하는 프로모션 조회 (프로모션이 할인율과 할인비용을 동시에 가질 때)")
    public void getItemAndPromotionWithDoubleDiscount() {
        Item item = getStubItems().get(0);
        int itemPrice = item.getItemPrice();
        Promotion promotion = getStubPromotions().get(1);
        setMapPromotionItem(item, promotion);

        int discountAmount = promotion.getDiscountAmount() != null ? promotion.getDiscountAmount(): 0;
        float discountRate = promotion.getDiscountRate() != null ? promotion.getDiscountRate(): 0;
        int price0 = itemPrice - discountAmount;
        int price1 = (int) (itemPrice * (1 - discountRate));
        price0 = price0 > 0 ? price0 : itemPrice;
        price1 = price1 > 0 ? price1 : itemPrice;

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));

        ItemWithPromotionDto itemWithPromotion = itemService.getItemWithPromotion(item.getId());
        assertThat(itemWithPromotion.getPromotionResDto()).isEqualTo(promotion.convertPromotionToResDto());
        assertThat(itemWithPromotion.getItemDiscountDto().getDiscountedPrice()).isEqualTo(Math.min(price0, price1));
    }

    @Test
    @DisplayName("상품과 그에 해당하는 프로모션 조회 (프로모션 날짜가 해당 안됨)")
    public void getItemAndPromotionWithWrongDate() {
        Item item = getStubItems().get(0);
        int itemPrice = item.getItemPrice();
        Promotion promotion = getStubPromotions().get(3);
        setMapPromotionItem(item, promotion);

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));

        ItemWithPromotionDto itemWithPromotion = itemService.getItemWithPromotion(item.getId());
        assertThat(itemWithPromotion.getPromotionResDto()).isEqualTo(null);
        assertThat(itemWithPromotion.getItemDiscountDto().getDiscountedPrice()).isEqualTo(itemPrice);
    }

    @Test
    @DisplayName("상품과 그에 해당하는 프로모션 조회 성공 (프로모션 수 복수)")
    public void getItemWithPromotions() {
        Item item = getStubItems().get(0);
        Promotion promotion0 = getStubPromotions().get(0);
        Promotion promotion1 = getStubPromotions().get(1);
        setMapPromotionItem(item, promotion0);
        setMapPromotionItem(item, promotion1);

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));

        ItemWithPromotionDto itemWithPromotion = itemService.getItemWithPromotion(item.getId());
        assertThat(itemWithPromotion.getPromotionResDto()).isEqualTo(promotion1.convertPromotionToResDto());
        assertThat(itemWithPromotion.getItemDiscountDto().getDiscountedPrice()).isEqualTo(50);
    }

    private void setMapPromotionItem(Item item, Promotion promotion0) {
        MapPromotionItem mapPromotionItem0 = new MapPromotionItem();
        mapPromotionItem0.setPromotion(promotion0);
        mapPromotionItem0.setItem(item);
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

    private List<User> getStubUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(0, "name0", UserType.NORMAL_USER, UserStat.NORMAL));
        users.add(new User(1, "name1", UserType.CORPORATE_USER, UserStat.WITHDRAWAL));
        users.add(new User(2, "name1", UserType.CORPORATE_USER, UserStat.NORMAL));
        return users;
    }


    private List<Promotion> getStubPromotions() {
        Timestamp time0 = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Timestamp time1 = Timestamp.valueOf(LocalDateTime.now().plusMonths(1).withNano(0));
        Timestamp time2 = Timestamp.valueOf(LocalDateTime.now().plusMonths(2).withNano(0));
        Timestamp time3 = Timestamp.valueOf(LocalDateTime.now().plusMonths(2).withNano(0));
        Timestamp beforeNow = Timestamp.valueOf(LocalDateTime.now().minusMonths(1).withNano(0));

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion(0, "name0", null, 0.05f, time0, time1));
        promotions.add(new Promotion(1, "name1", 1000, 0.5f, time0, time2));
        promotions.add(new Promotion(2, "name2", 100000, null, time0, time3));
        promotions.add(new Promotion(3, "name0", null, 0.05f, beforeNow, time0));
        return promotions;
    }
}
