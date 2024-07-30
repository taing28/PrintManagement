package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.printmanagement.model.entities.Design;
import org.example.printmanagement.model.entities.PrintJob;
import org.example.printmanagement.model.entities.PrintJobStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class PrintJobRequest {
    private int id;
    private int designId;
    private String printJobStatus;

    public PrintJob toEntity(){
        PrintJob pj = new PrintJob();
        if(this.id != 0) {
            pj.setId(this.id);
        }
        pj.setDesign(new Design(this.designId));
        pj.setDesignId(this.designId);

        switch (this.printJobStatus) {
            case "confirm":
                pj.setPrintJobStatus(PrintJobStatus.CONFIRM);
                break;
            default:
                pj.setPrintJobStatus(PrintJobStatus.PENDING);
        }
        return pj;
    }
}
