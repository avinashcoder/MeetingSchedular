package digitechunicorn.meetingschedular.com.meetingschedular;

public class MeetingList {

    private int id;
    private String CompanyName;
    private String ContactPerson;
    private String ListDate;
    private String ListTime;
    private String ListId;


    public MeetingList(int id, String companyName, String contactPerson, String listDate, String listTime, String listId) {
        this.id = id;
        CompanyName = companyName;
        ContactPerson = contactPerson;
        ListDate = listDate;
        ListTime = listTime;
        ListId = listId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getListDate() {
        return ListDate;
    }

    public void setListDate(String listDate) {
        ListDate = listDate;
    }

    public String getListTime() {
        return ListTime;
    }

    public void setListTime(String listTime) {
        ListTime = listTime;
    }

    public String getListId() {
        return ListId;
    }

    public void setListId(String listId) {
        ListId = listId;
    }
}
