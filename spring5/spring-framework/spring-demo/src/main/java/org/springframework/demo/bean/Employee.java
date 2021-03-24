package org.springframework.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@JsonIgnoreProperties(value = {"handler"})
public class Employee implements Serializable{
	private static final long serialVersionUID = -7530890724264444784L;
	private Integer empId;
    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})", message = "用户名必须是2-5位中文或6-16位英文和数字的组合！")
    private String empName;

    private String gender;
    @Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$", message = "邮箱格式不正确！")
    private String email;

    private Integer dId;

    private Department department;

    public Employee() {
    }

    public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.email = email;
        this.dId = dId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + this.empId +
                ", empName='" + this.empName + '\'' +
                ", gender='" + this.gender + '\'' +
                ", email='" + this.email + '\'' +
                ", dId=" + this.dId +
                ", department=" + this.department +
                '}';
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getEmpId() {
        return this.empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return this.dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }
}