package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResourceForPrintConfirmRequest {
    private int billId;
    private int designId;
    private List<ConfirmResourceRequest> requestList;
}
