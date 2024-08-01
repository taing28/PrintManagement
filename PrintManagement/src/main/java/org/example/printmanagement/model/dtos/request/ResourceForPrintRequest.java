package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.PrintJob;
import org.example.printmanagement.model.entities.ResourceForPrintJob;
import org.example.printmanagement.model.entities.ResourcePropertyDetail;

@Data
@NoArgsConstructor
public class ResourceForPrintRequest {
    private int id;
    private int printJobId;
    private int resourcePropertyDetailId;
    private int quantity;

    public ResourceForPrintJob toResourceForPrintJob() {
        ResourceForPrintJob forPrintJob = new ResourceForPrintJob();
        if(this.id != 0) {
            forPrintJob.setId(this.id);
        }
        forPrintJob.setPrintJobId(this.printJobId);
        forPrintJob.setPrintJob(new PrintJob(this.printJobId));
        forPrintJob.setResourcePropertyDetailId(this.resourcePropertyDetailId);
        forPrintJob.setResourcePropertyDetailPrint(new ResourcePropertyDetail(this.resourcePropertyDetailId));
        return forPrintJob;
    }
}
