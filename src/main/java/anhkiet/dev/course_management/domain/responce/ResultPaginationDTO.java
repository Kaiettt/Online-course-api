package anhkiet.dev.course_management.domain.responce;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResultPaginationDTO {
    private Meta meta;
    private Object result;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Meta {
        private int page;
        private int pageSize;
        private int pages;
        private long total;

    }


}
