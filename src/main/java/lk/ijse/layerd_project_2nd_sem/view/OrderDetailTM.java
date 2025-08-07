package lk.ijse.layerd_project_2nd_sem.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDetailTM {
    private String customerContact;
    private String itemId;
    private String itemName;
    private int quantity;
    private double unitPrice;
    private double total;

    public OrderDetailTM(String customerContact, String itemId, String name, int qty, Double unitPrice, Double total) {
    }

    public OrderDetailTM(String itemCode, String description, int qty, double unitPrice, double total) {
    }
}
