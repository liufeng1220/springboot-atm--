package co.fengfeng.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AjaxRes {
    private String res;
    private String meg;
    private Object object;
}
