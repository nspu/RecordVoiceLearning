package fr.nspu.dev.recordvoicelearning.controller.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import fr.nspu.dev.recordvoicelearning.model.Peer;

/**
 * Created by nspu on 18-02-02.
 */

@Entity(tableName = "peers",foreignKeys = {
            @ForeignKey(entity = FolderEntity.class,
                        parentColumns = "id",
                        childColumns = "folder_Id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "folder_Id")})
public class PeerEntity implements Peer, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "folder_Id")
    private int folderId;

    @ColumnInfo(name = "file_name_question")
    private String fileNameQuestion;

    @ColumnInfo(name = "file_name_answer")
    private String fileNameAnswer;

    @ColumnInfo(name = "knowledge")
    private int knowledge;

    @ColumnInfo(name="count")
    private int count;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Override
    public int getFolderId() {
        return this.folderId;
    }

    @Override
    public String getFileNameQuestion() {
        return this.fileNameQuestion;
    }

    @Override
    public String getFileNameAnswer() {
        return this.fileNameAnswer;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getKnowledge() {
        return this.knowledge;
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
        this.updatedAt = new Date();
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
        this.updatedAt = new Date();
    }

    public void setFileNameQuestion(String fileNameQuestion) {
        this.fileNameQuestion = fileNameQuestion;
        this.updatedAt = new Date();
    }

    public void setFileNameAnswer(String fileNameAnswer) {
        this.fileNameAnswer = fileNameAnswer;
        this.updatedAt = new Date();
    }

    public void setKnowledge(int knowledge) {
        if(knowledge > 10){
            this.knowledge = 10;
        }else if(knowledge < 0){
            this.knowledge = 0;
        }else{
            this.knowledge = knowledge;
        }
        this.updatedAt = new Date();
    }

    public void setCount(int count){
        this.count = count;
        this.updatedAt = new Date();
    }

    public void increaseKnowledge(){
        if(knowledge < 10){
            knowledge++;
        }
        this.count++;
        this.updatedAt = new Date();
    }

    public void descreaseKnowledge(){
        if(knowledge > 0){
            knowledge--;
        }
        this.count++;
        this.updatedAt = new Date();
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public PeerEntity() {
        this(0, 0, "", "", 0, 0);
    }

    @Ignore
    public PeerEntity(int id, int folderId, String fileNameQuestion, String fileNameAnswer, int knowledge, int count) {
        this.id = id;
        this.folderId = folderId;
        this.fileNameQuestion = fileNameQuestion;
        this.fileNameAnswer = fileNameAnswer;
        this.knowledge = knowledge;
        this.count = count;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
