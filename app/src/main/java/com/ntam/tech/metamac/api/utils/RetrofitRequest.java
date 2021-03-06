package com.ntam.tech.metamac.api.utils;

import android.util.Log;

import com.ntam.tech.metamac.api.modelRequest.AddNotificationRequest;
import com.ntam.tech.metamac.api.modelRequest.ParentRequest;
import com.ntam.tech.metamac.api.modelResponse.AboutResponse;
import com.ntam.tech.metamac.api.modelResponse.AddPhotoResponse;
import com.ntam.tech.metamac.api.modelResponse.AgendaListResponse;
import com.ntam.tech.metamac.api.modelResponse.AnnouncementListResponse;
import com.ntam.tech.metamac.api.modelResponse.AttendeeListResponse;
import com.ntam.tech.metamac.api.modelResponse.ChatListResponse;
import com.ntam.tech.metamac.api.modelResponse.CommentResponse;
import com.ntam.tech.metamac.api.modelResponse.LoginResponse;
import com.ntam.tech.metamac.api.modelResponse.MessageCountResponse;
import com.ntam.tech.metamac.api.modelResponse.MessageDetailsResponse;
import com.ntam.tech.metamac.api.modelResponse.NotificationListResponse;
import com.ntam.tech.metamac.api.modelResponse.ParentResponse;
import com.ntam.tech.metamac.api.modelResponse.PhotoListResponse;
import com.ntam.tech.metamac.api.modelResponse.PostListResponse;
import com.ntam.tech.metamac.api.modelResponse.PostResponse;
import com.ntam.tech.metamac.api.modelResponse.QuestionResponse;
import com.ntam.tech.metamac.api.modelResponse.SpeakerListResponse;
import com.ntam.tech.metamac.api.modelResponse.WebViewResponse;
import com.ntam.tech.metamac.model.About;
import com.ntam.tech.metamac.model.Agenda;
import com.ntam.tech.metamac.model.Announcement;
import com.ntam.tech.metamac.model.AttendeLisWithLetter;
import com.ntam.tech.metamac.model.Chat;
import com.ntam.tech.metamac.model.Comment;
import com.ntam.tech.metamac.model.Message;
import com.ntam.tech.metamac.model.Photo;
import com.ntam.tech.metamac.model.Post;
import com.ntam.tech.metamac.model.Question;
import com.ntam.tech.metamac.model.Speaker;
import com.ntam.tech.metamac.model.User;
import com.ntam.tech.metamac.model.UserNotification;
import com.google.gson.Gson;
import com.ntam.tech.metamac.model.WebViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ahmed on 10/10/17.
 */

public class RetrofitRequest {

    private static RetrofitService service = RetrofitConfi.getRetrofit().create(RetrofitService.class);
    private static final String errorMessageForDevelopment = "Sorry we can\'t load data";
    private static final String INTERNET_CONNECTION = "Error No Internet Connection";
    private static final String TAG = "TAG";

    public static void login(String email, String code, String token, final RetrofitResponse<User> userRetrofitResponse) {
        Call<LoginResponse> response = service.login(email, code, token, ParentRequest.getEventId());
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        userRetrofitResponse.onSuccess(response.body().getUser());
                    } else {
                        userRetrofitResponse.onFailed("Email Or Password is incorrect , please retry!");
                    }
                } else {
                    userRetrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                userRetrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });
    }

    public static void getPosts(int userId, int pageNumber, final RetrofitResponse<List<Post>> listRetrofitResponse) {
        Call<PostListResponse> response = service.getAllPost(userId, pageNumber, ParentRequest.getEventId());
        response.enqueue(new Callback<PostListResponse>() {
            @Override
            public void onResponse(Call<PostListResponse> call, Response<PostListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        listRetrofitResponse.onSuccess(response.body().getPosts());
                    } else {
                        listRetrofitResponse.onFailed(response.body().getMassage());
                    }
                } else {
                    listRetrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<PostListResponse> call, Throwable t) {
                listRetrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });
    }

    private static void addPostOrCheckIn(int userId, String post, boolean checkIn, final RetrofitResponse<Post> retrofitResponse) {
        Call<PostResponse> response;

        if (checkIn) // this case make check in so i send 1 as true and empty post
            response = service.addPostOrCheckIn(userId, "", 1, ParentRequest.getEventId());
        else // this case make post in so i send 0 as false
            response = service.addPostOrCheckIn(userId, post, 0, ParentRequest.getEventId());
        response.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getPost());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                    }
                } else {
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });
    }

    public static void addPost(int userId, String post, final RetrofitResponse<Post> retrofitResponse) {
        addPostOrCheckIn(userId, post, false, retrofitResponse);
    }

    public static void checkIn(int userId, final RetrofitResponse<Post> retrofitResponse) {
        addPostOrCheckIn(userId, "", true, retrofitResponse);
    }

    public static void getAllSpeaker(int userId, final RetrofitResponse<List<Speaker>> retrofitResponse) {
        Call<SpeakerListResponse> response = service.getAllSpeaker(userId, ParentRequest.getEventId());
        response.enqueue(new Callback<SpeakerListResponse>() {
            @Override
            public void onResponse(Call<SpeakerListResponse> call, Response<SpeakerListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getSpeakers());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                    }
                } else {
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<SpeakerListResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });
    }

    public static void getAllAttendee(int pageNumber, String searchWord, final RetrofitResponse<List<AttendeLisWithLetter>> retrofitResponse) {
        Call<AttendeeListResponse> response;
        if (!searchWord.trim().isEmpty()) {
            response = service.searchAttendee(pageNumber, ParentRequest.getEventId(), searchWord);
        } else {
            response = service.getAllAttendee(pageNumber, ParentRequest.getEventId());
        }
        response.enqueue(new Callback<AttendeeListResponse>() {
            @Override
            public void onResponse(Call<AttendeeListResponse> call, Response<AttendeeListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getAttendeesWithLetter());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                    }
                } else {
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<AttendeeListResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });
    }

    public static void getAllAgenda(int userId, final RetrofitResponse<List<Agenda>> retrofitResponse) {
        Call<AgendaListResponse> response = service.getAllAgenda(userId, ParentRequest.getEventId());
        response.enqueue(new Callback<AgendaListResponse>() {
            @Override
            public void onResponse(Call<AgendaListResponse> call, Response<AgendaListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getAgendaList());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                    }
                } else {
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<AgendaListResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });
    }

    public static void getMessageDetails(int userId, int otherUserId, final RetrofitResponse<List<Message>> retrofitResponse) {
        Call<MessageDetailsResponse> response = service.getMessageDetails(userId, otherUserId, ParentRequest.getEventId());
        response.enqueue(new Callback<MessageDetailsResponse>() {
            @Override
            public void onResponse(Call<MessageDetailsResponse> call, Response<MessageDetailsResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getMessageList());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                    }
                } else {
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<MessageDetailsResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });
    }

    public static void sendMessage(int userId, int otherUserId, String message, final RetrofitResponse<Boolean> retrofitResponse) {
        Call<ParentResponse> response = service.sendMessage(userId, otherUserId, message, "", ParentRequest.getEventId());
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    retrofitResponse.onSuccess(response.body().getStatus());
                } else {
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
            }
        });

    }
    public static void addToMyAgenda(int userId,int sessionId,final RetrofitResponse<Boolean> retrofitResponse){
        Call<ParentResponse> response = service.addToMyAgenda(sessionId, userId, ParentRequest.getEventId());
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    retrofitResponse.onSuccess(response.body().getStatus());
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });

    }
    public static void getChatList(int userId, final RetrofitResponse<List<Chat>> retrofitResponse){
        Call<ChatListResponse> response = service.getChatList(userId, ParentRequest.getEventId());
        response.enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getChatLists());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });

    }
    public static void getMessageCount(int userId,final RetrofitResponse<Integer> retrofitResponse){
        Call<MessageCountResponse> response = service.getMessageCount(userId, ParentRequest.getEventId());
        response.enqueue(new Callback<MessageCountResponse>() {
            @Override
            public void onResponse(Call<MessageCountResponse> call, Response<MessageCountResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getTotalMessage());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<MessageCountResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });

    }
    public static void addLikeToPost(int userId,int postId,final RetrofitResponse<Boolean> retrofitResponse){
        Call<ParentResponse> response = service.addLikeToPost(userId,postId, ParentRequest.getEventId());
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getStatus());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });
    }
    public static void getListNotification(int userId, final RetrofitResponse<List<UserNotification>> retrofitResponse){
        Call<NotificationListResponse> response = service.getListNotification(userId, ParentRequest.getEventId());
        response.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getUserNotificationList());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage()+"");
                    }
                } else {
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {
                Log.e("onResponse: ", errorMessageForDevelopment);
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });
    }
    public static void getAbout(final RetrofitResponse<About> retrofitResponse){
        Call<AboutResponse> response = service.getAbout(ParentRequest.getEventId());
        response.enqueue(new Callback<AboutResponse>() {
            @Override
            public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getAbout());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<AboutResponse> call, Throwable t) {
                Log.e("onResponse: ", errorMessageForDevelopment);
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });

    }

    public static void getPhotoList(int userId,final RetrofitResponse<List<Photo>> retrofitResponse){
        Call<PhotoListResponse> response = service.getPhotoList(userId,ParentRequest.getEventId());
        response.enqueue(new Callback<PhotoListResponse>() {
            @Override
            public void onResponse(Call<PhotoListResponse> call, Response<PhotoListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getPhotos());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<PhotoListResponse> call, Throwable t) {
                Log.e("onResponse: ", errorMessageForDevelopment);
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });
    }
    public static void addPhoto(int userId,String imagePathEncode,final RetrofitResponse<Photo> retrofitResponse){
        Call<AddPhotoResponse> response = service.addPhoto(userId,ParentRequest.getEventId(),imagePathEncode);
        response.enqueue(new Callback<AddPhotoResponse>() {
            @Override
            public void onResponse(Call<AddPhotoResponse> call, Response<AddPhotoResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        Photo photo = new Photo(response.body().getPhotoId(),response.body().getImage());
                        retrofitResponse.onSuccess(photo);
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", response.body().getMassage());
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }
            @Override
            public void onFailure(Call<AddPhotoResponse> call, Throwable t) {
                Log.e("onResponse: ", t.getLocalizedMessage()+"");
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });
    }
    public static void getAnnouncementList(final RetrofitResponse<List<Announcement>> retrofitResponse){
        Call<AnnouncementListResponse> response = service.getAnnouncementList(ParentRequest.getEventId());
        response.enqueue(new Callback<AnnouncementListResponse>() {
            @Override
            public void onResponse(Call<AnnouncementListResponse> call, Response<AnnouncementListResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getAnnouncements());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<AnnouncementListResponse> call, Throwable t) {
                Log.e("onResponse: ", errorMessageForDevelopment);
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });
    }
    public static void addNotification(String title, String body, ArrayList<String> targets, final RetrofitResponse<Boolean> retrofitResponse){
        String targetString="{\"targets\":[";
        for(String item:targets){
            targetString+="{"+"\"target_id\":\""+item+"\"}";
            targetString+=",";
        }
        targetString = targetString.substring(0, targetString.length() - 1);
        targetString=targetString+"]}";
        Log.e( "addNotification: ",targetString );
        Call<ParentResponse> response = service.addNotification(ParentRequest.getEventId(),title,body, AddNotificationRequest.addToDraft(),targetString);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getStatus());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });
    }
    public static void addLikeToPhoto(int userId,int photoId,final RetrofitResponse<Boolean> retrofitResponse){
        Call<ParentResponse> response = service.addLikeToPhoto(userId,photoId, ParentRequest.getEventId());
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getStatus());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });
    }
    public static void addCommentToPhoto(int userId,String comment,int photoId,final RetrofitResponse<Comment> retrofitResponse){
        Call<CommentResponse> response = service.addCommentToPhoto(userId,comment,photoId, ParentRequest.getEventId());
        response.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getComment());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });

    }
    public static void addCommentToPost(int userId,String comment,int postId,final RetrofitResponse<Comment> retrofitResponse){
        Call<CommentResponse> response = service.addCommentToPost(userId,comment,postId, ParentRequest.getEventId());
        response.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getComment());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });
    }
    public static void updateUserInfo(int userId,String country,String email,String mobile,String hospital,final RetrofitResponse<String> retrofitResponse){
        Call<ParentResponse> response = service.updateUserInfo(userId, ParentRequest.getEventId(),country,email,mobile,hospital);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getMassage());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });
    }
    public static void updateMessageStatus(int userId, int otherUserId, final RetrofitResponse<Boolean> retrofitResponse){
        Call<ParentResponse> response = service.updateMessageStatus(userId,otherUserId,ParentRequest.getEventId());
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getStatus());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });

    }
    public static void updateBio(int userId, String bio, final RetrofitResponse<Boolean> retrofitResponse){
        Call<ParentResponse> response = service.updateBio(userId,bio,ParentRequest.getEventId());
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getStatus());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });

    }
    public static void editUserImage(int userId,String imagePathEncode,final RetrofitResponse<AddPhotoResponse> retrofitResponse){
        Call<AddPhotoResponse> response = service.editUserImage(userId,ParentRequest.getEventId(),imagePathEncode);
        response.enqueue(new Callback<AddPhotoResponse>() {
            @Override
            public void onResponse(Call<AddPhotoResponse> call, Response<AddPhotoResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", response.body().getMassage()+"");
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }
            @Override
            public void onFailure(Call<AddPhotoResponse> call, Throwable t) {
                Log.e("onResponse: ", t.getLocalizedMessage()+"");
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });
    }

    public static void answerQuestion(int questionId,int answerId,int userId,final RetrofitResponse retrofitResponse){
        Call<ParentResponse> response = service.answerQuestion(questionId,answerId,userId);
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", response.body().getMassage()+"");
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }
            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                Log.e("onResponse: ", t.getLocalizedMessage()+"");
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });
    }

    public static void getQuestion(int questionId,final RetrofitResponse<QuestionResponse> retrofitResponse){
        Call<QuestionResponse> response = service.getQuestion("get_question.php?question_id="+questionId);
        response.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", response.body().getMassage()+"");
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }
            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Log.e("onResponse: ", t.getLocalizedMessage()+"");
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });
    }

    public static void getNewRequests(String url,final RetrofitResponse<WebViewModel> retrofitResponse){
        Call<WebViewResponse> response = service.getNewRequests(url);
        response.enqueue(new Callback<WebViewResponse>() {
            @Override
            public void onResponse(Call<WebViewResponse> call, Response<WebViewResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus()) {
                        retrofitResponse.onSuccess(response.body().getWebViewModel());
                    } else {
                        retrofitResponse.onFailed(response.body().getMassage());
                        Log.e(TAG, response.body().getMassage());
                    }
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<WebViewResponse> call, Throwable t) {
                Log.e("onResponse: ", errorMessageForDevelopment);
                retrofitResponse.onFailed(errorMessageForDevelopment);
            }
        });

    }

    public static void removeToMyAgenda(int userId,int sessionId,final RetrofitResponse<Boolean> retrofitResponse){
        Call<ParentResponse> response = service.removeToMyAgenda(sessionId, userId, ParentRequest.getEventId());
        response.enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                if (response.code() == 200) {
                    retrofitResponse.onSuccess(response.body().getStatus());
                } else {
                    Log.e("onResponse: ", errorMessageForDevelopment);
                    retrofitResponse.onFailed(errorMessageForDevelopment);
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                retrofitResponse.onFailed(INTERNET_CONNECTION);
                Log.e(TAG, t.getLocalizedMessage() + "");
            }
        });

    }
}
