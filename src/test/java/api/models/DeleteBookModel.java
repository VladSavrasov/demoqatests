package api.models;

import lombok.Data;

import java.util.List;
@Data
public class DeleteBookModel {
    String userId;
    String isbn;
}
