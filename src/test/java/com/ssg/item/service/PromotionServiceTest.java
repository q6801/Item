package com.ssg.item.service;

import com.ssg.item.dto.PromotionDto;
import com.ssg.item.dto.PromotionResDto;
import com.ssg.item.entity.Promotion;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.repository.PromotionRepository;
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
public class PromotionServiceTest {
    private static Validator validator;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Mock
    private PromotionRepository promotionRepository;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("setPromotion 성공")
    public void setPromotion() {
        Promotion promotion = getStubPromotions().get(0);
        PromotionDto inputData = new PromotionDto(promotion.getName(), promotion.getDiscountAmount(), promotion.getDiscountRate(),
                promotion.getPromotionStartDate(), promotion.getPromotionEndDate());

        given(promotionRepository.save(promotion)).willReturn(promotion);
        PromotionResDto promotionResDto = promotionService.setPromotion(inputData);

        assertThat(promotionResDto.getName()).isEqualTo(promotion.getName());
        assertThat(promotionResDto.getDiscountAmount()).isEqualTo(promotion.getDiscountAmount());
        assertThat(promotionResDto.getDiscountRate()).isEqualTo(promotion.getDiscountRate());
        assertThat(promotionResDto.getPromotionStartDate()).isEqualTo(promotion.getPromotionStartDate());
        assertThat(promotionResDto.getPromotionEndDate()).isEqualTo(promotion.getPromotionEndDate());
    }

    @Test
    @DisplayName("promotion 시작시간이 종료시간보다 늦거나 같은 경우")
    public void setPromotionWithWrongDate() {
        String promotionName = "promotion name";
        int promotionAmount = 1000;
        Float promotionRate = null;
        Timestamp first = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Timestamp second = Timestamp.valueOf(LocalDateTime.now().plusMonths(1).withNano(0));

        Assertions.assertThatThrownBy(() -> new Promotion(promotionName, promotionAmount, promotionRate, second, first))
                .isInstanceOf(CustomRuntimeException.class);

        Assertions.assertThatThrownBy(() -> new PromotionDto(promotionName, promotionAmount, promotionRate, second, first))
                .isInstanceOf(CustomRuntimeException.class);

        new Promotion(promotionName, promotionAmount, promotionRate, first, second);
        new Promotion(promotionName, promotionAmount, promotionRate, first, first);
        new PromotionDto(promotionName, promotionAmount, promotionRate, first, second);
        new PromotionDto(promotionName, promotionAmount, promotionRate, second, second);
    }

    @Test
    @DisplayName("promotionDto에 discount의 amount와 rate가 동시에 null값이 들어간 경우")
    public void setItemWithWrongInput() {
        String promotionName = "promotion name";
        Timestamp first = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Timestamp second = Timestamp.valueOf(LocalDateTime.now().plusMonths(1).withNano(0));

        Assertions.assertThatThrownBy(() -> new PromotionDto(promotionName, null, null, first, second))
                .isInstanceOf(CustomRuntimeException.class);
    }

    @Test
    @DisplayName("promotionDto에 null 혹은 범위를 벗어난 값이 들어간 경우")
    public void setItemWithWrongDiscount() {
        String promotionName = "promotion name";
        Timestamp first = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Timestamp second = Timestamp.valueOf(LocalDateTime.now().plusMonths(1).withNano(0));

        PromotionDto dto0 = new PromotionDto("", -123, null, null, null);
        PromotionDto dto1 = new PromotionDto(null, 1000, 10f, null, null);
        PromotionDto dto2 = new PromotionDto(null, 1000, -0.5f, null, null);

        Set<ConstraintViolation<PromotionDto>> validate0 = validator.validate(dto0);
        assertThat(validate0.size()).isEqualTo(4);
        Set<ConstraintViolation<PromotionDto>> validate1 = validator.validate(dto1);
        assertThat(validate1.size()).isEqualTo(4);
        Set<ConstraintViolation<PromotionDto>> validate2 = validator.validate(dto2);
        assertThat(validate2.size()).isEqualTo(4);

        new PromotionDto(promotionName, null, 0.0f, first, second);
        new PromotionDto(promotionName, null, 1.0f, first, second);
        new PromotionDto(promotionName, 0, null, first, second);


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

}
