package lk.ijse.layerd_project_2nd_sem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDTO {
    private String orderId;
    private String customerContact;
    private String date;
    private String customerName;
    private String orderTotal;

    public OrderDTO(String orderId , String customerContact , String date ){
        this.orderId = orderId;
        this.customerContact = customerContact;
        this.date = date;
    }

}
