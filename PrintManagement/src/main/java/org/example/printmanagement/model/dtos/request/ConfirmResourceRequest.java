package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfirmResourceRequest {
    private int id;
    private int quantity;
}
