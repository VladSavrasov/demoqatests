package api.models;

import lombok.Data;

@Data
public class DeleteBookModel {
    String userId;
    String isbn;
}
