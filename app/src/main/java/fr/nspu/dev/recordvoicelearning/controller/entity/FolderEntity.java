package fr.nspu.dev.recordvoicelearning.controller.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import fr.nspu.dev.recordvoicelearning.model.Folder;

/**
 * Created by nspu on 18-02-02.
 */

@Entity(tableName = "folders")
public class  FolderEntity implements Folder, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type_question")
    private String typeQuestion;

    @ColumnInfo(name = "type_answer")
    private String typeAnswer;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTypeQuestion() {
        return this.typeQuestion;
    }

    @Override
    public String getTypeAnswer() {
        return this.typeAnswer;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeQuestion(String typeQuesion) {
        this.typeQuestion = typeQuesion;
    }

    public void setTypeAnswer(String typeAnswer) {
        this.typeAnswer = typeAnswer;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FolderEntity() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public FolderEntity(int id, String name, String typeQuesion, String typeAnswer) {
        this.id = id;
        this.name = name;
        this.typeQuestion = typeQuesion;
        this.typeAnswer = typeAnswer;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
