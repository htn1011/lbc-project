package com.kenzie.appserver.controller.model;




import com.fasterxml.jackson.annotation.JsonProperty;





import javax.validation.constraints.NotEmpty;

public class TaskUpdateRequest {

    @NotEmpty
    @JsonProperty("id")
    private String id;

    @NotEmpty
    @JsonProperty("name")
    private String name;






//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("dateAdded")
    private String dateAdded;



//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")





    @JsonProperty("completionDate")
    private String completionDate;

    @JsonProperty("completed")




    private boolean completed;








    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }




    public boolean isCompleted() {

        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}




