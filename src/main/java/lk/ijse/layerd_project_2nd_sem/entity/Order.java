package lk.ijse.layerd_project_2nd_sem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Order {
    private String orderId;
    private String customerContact;
    private String date;
    private String customerName;
    private String orderTotal;

    public Order(String orderId, String customerContact, LocalDate date) {
        this.orderId = orderId;
        this.customerContact = customerContact;
        this.date = date.toString();
    }
}
