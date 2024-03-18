
package model;

@lombok.Getter
@lombok.Setter 
@lombok.AllArgsConstructor 
@lombok.NoArgsConstructor
@lombok.Data 
public class Review {
    private int id;
    private int productId;
    private int userId;
    private int reviewText;
    private int rating;
}
