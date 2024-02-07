package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class GetAllBooksResponseModel {
    IsbnModel[] books;
}
