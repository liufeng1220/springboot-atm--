package co.fengfeng.common.exception;

import co.fengfeng.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AtmException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
