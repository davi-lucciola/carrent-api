package io.api.carrent.domain.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Pagination<T> {
    private List<T> data;
    private Integer total;
    private Integer page;
    private Integer perPage;
    private Integer totalPages;

    public Pagination(List<T> data, Integer total, Integer page, Integer perPage) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.perPage = perPage;
        this.totalPages = (int) Math.ceil((double) total / perPage);
    }
}
