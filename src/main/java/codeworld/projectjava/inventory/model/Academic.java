package codeworld.projectjava.inventory.model;

public class Academic {
    
    private Long id;
    private String academicYear;
    private String programme;
    private String cirtificateType;
    private String otherDocs;
    

    public Academic() {}

    public Academic(Long id, 
                   String academicYear,
                   String programme,
                   String cirtificateType,
                   String otherDocs){

                   this.id = id;
                   this.academicYear = academicYear;
                   this.programme = programme;
                   this.cirtificateType = cirtificateType;
                   this.otherDocs = otherDocs;

                   }
    
    public Long getId(){return id;}
    public String getAcademicYear(){return academicYear;}
    public String getProgramme(){return programme;}
    public String getCirtificateType(){return cirtificateType;}
    public String getOtherDocs(){return otherDocs;}

    public void setId(Long id){this.id = id;}
    public void setAcademicYear(String academicYear){this.academicYear = academicYear;}
    public void setProgramme(String programme){this.programme = programme;}
    public void setCirtificateType(String cirtificateType){this.cirtificateType = cirtificateType;}
    public void setOtherDocs(String otherDocs){this.otherDocs = otherDocs;}
}
