package com.example.englishquiz;

import static com.example.englishquiz.R.string.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Questions {

    private static final Question[] questionBank = new Question[]{
            new Question(q_1, q_1_op_1, q_1_op_2, q_1_explanation, q_1_answer),
            new Question(q_2, q_2_op_1, q_2_op_2, q_2_explanation, q_2_answer),
            new Question(q_3, q_3_op_1, q_3_op_2, q_3_explanation, q_3_answer),
            new Question(q_4, q_4_op_1, q_4_op_2, q_4_explanation, q_4_answer),
            new Question(q_5, q_5_op_1, q_5_op_2, q_5_explanation, q_5_answer),
            new Question(q_6, q_6_op_1, q_6_op_2, q_6_explanation, q_6_answer),
            new Question(q_7, q_7_op_1, q_7_op_2, q_7_explanation, q_7_answer),
            new Question(q_8, q_8_op_1, q_8_op_2, q_8_explanation, q_8_answer),
            new Question(q_9, q_9_op_1, q_9_op_2, q_9_explanation, q_9_answer),
            new Question(q_10, q_10_op_1, q_10_op_2, q_10_explanation, q_10_answer),
            new Question(q_11, q_11_op_1, q_11_op_2, q_11_explanation, q_11_answer),
            new Question(q_12, q_12_op_1, q_12_op_2, q_12_explanation, q_12_answer),
            new Question(q_13, q_13_op_1, q_13_op_2, q_13_explanation, q_13_answer),
            new Question(q_14, q_14_op_1, q_14_op_2, q_14_explanation, q_14_answer),
            new Question(q_15, q_15_op_1, q_15_op_2, q_15_explanation, q_15_answer),
            new Question(q_16, q_16_op_1, q_16_op_2, q_16_explanation, q_16_answer),
            new Question(q_17, q_17_op_1, q_17_op_2, q_17_explanation, q_17_answer),
            new Question(q_18, q_18_op_1, q_18_op_2, q_18_explanation, q_18_answer),
            new Question(q_19, q_19_op_1, q_19_op_2, q_19_explanation, q_19_answer),
            new Question(q_20, q_20_op_1, q_20_op_2, q_20_explanation, q_20_answer),
            new Question(q_21, q_21_op_1, q_21_op_2, q_21_explanation, q_21_answer),
            new Question(q_22, q_22_op_1, q_22_op_2, q_22_explanation, q_22_answer),
            new Question(q_23, q_23_op_1, q_23_op_2, q_23_explanation, q_23_answer),
            new Question(q_24, q_24_op_1, q_24_op_2, q_24_explanation, q_24_answer),
            new Question(q_25, q_25_op_1, q_25_op_2, q_25_explanation, q_25_answer),
            new Question(q_26, q_26_op_1, q_26_op_2, q_26_explanation, q_26_answer),
            new Question(q_27, q_27_op_1, q_27_op_2, q_27_explanation, q_27_answer),
            new Question(q_28, q_28_op_1, q_28_op_2, q_28_explanation, q_28_answer),
            new Question(q_29, q_29_op_1, q_29_op_2, q_29_explanation, q_29_answer),
            new Question(q_30, q_30_op_1, q_30_op_2, q_30_explanation, q_30_answer),
            new Question(q_31, q_31_op_1, q_31_op_2, q_31_explanation, q_31_answer),
            new Question(q_32, q_32_op_1, q_32_op_2, q_32_explanation, q_32_answer),
            new Question(q_33, q_33_op_1, q_33_op_2, q_33_explanation, q_33_answer),
            new Question(q_34, q_34_op_1, q_34_op_2, q_34_explanation, q_34_answer),
            new Question(q_35, q_35_op_1, q_35_op_2, q_35_explanation, q_35_answer),
            new Question(q_36, q_36_op_1, q_36_op_2, q_36_explanation, q_36_answer),
            new Question(q_37, q_37_op_1, q_37_op_2, q_37_explanation, q_37_answer),
            new Question(q_38, q_38_op_1, q_38_op_2, q_38_explanation, q_38_answer),
            new Question(q_39, q_39_op_1, q_39_op_2, q_39_explanation, q_39_answer),
            new Question(q_40, q_40_op_1, q_40_op_2, q_40_explanation, q_40_answer),
            new Question(q_41, q_41_op_1, q_41_op_2, q_41_explanation, q_41_answer),
            new Question(q_42, q_42_op_1, q_42_op_2, q_42_explanation, q_42_answer),
            new Question(q_43, q_43_op_1, q_43_op_2, q_43_explanation, q_43_answer),
            new Question(q_44, q_44_op_1, q_44_op_2, q_44_explanation, q_44_answer),
            new Question(q_45, q_45_op_1, q_45_op_2, q_45_explanation, q_45_answer),
            new Question(q_46, q_46_op_1, q_46_op_2, q_46_explanation, q_46_answer),
            new Question(q_47, q_47_op_1, q_47_op_2, q_47_explanation, q_47_answer),
            new Question(q_48, q_48_op_1, q_48_op_2, q_48_explanation, q_48_answer),
            new Question(q_49, q_49_op_1, q_49_op_2, q_49_explanation, q_49_answer),
            new Question(q_50, q_50_op_1, q_50_op_2, q_50_explanation, q_50_answer)
    };

    public static Question[] getQuestionBank() {
        return questionBank;
    }

    public static Question[] get10RandomQuestions() {
        // Составляем список по порядку всех номеров вопросов
        List<Integer> allQuestions = new ArrayList<>();
        for (int i = 0; i < questionBank.length; i++) {
            allQuestions.add(i);
        }
        // Перемешиваем их
        Collections.shuffle(allQuestions);
        // Берём первые 10. Таким образом получаем случайные неповторящиеся числа
        Question[] result = new Question[10];
        for (int i = 0; i < result.length; i++) {
            result[i] = questionBank[allQuestions.get(i)];
        }
        return result;
    }

}
