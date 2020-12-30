package pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.CodeOrderQuestion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CodeOrderCorrectAnswerDto extends CorrectAnswerDetailsDto {
    private List<CodeOrderAnswerOrderedSlotDto> correctOrder;

    public CodeOrderCorrectAnswerDto(CodeOrderQuestion question) {
        this.correctOrder = question.getCodeOrderSlots()
                .stream()
                .map(CodeOrderAnswerOrderedSlotDto::new)
                .collect(Collectors.toList());
        this.correctOrder.sort(Comparator.comparing(CodeOrderAnswerOrderedSlotDto::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    public List<CodeOrderAnswerOrderedSlotDto> getCorrectOrder() {
        return correctOrder;
    }

    public void setCorrectOrder(List<CodeOrderAnswerOrderedSlotDto> correctOrder) {
        this.correctOrder = correctOrder;
    }
}