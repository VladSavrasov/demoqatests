package api.models;

import lombok.Data;

@Data
public class GetAllBooksResponseModel {
    IsbnModel[] books;
}
