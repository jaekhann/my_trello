package uz.pdp.jaecoder.controller;

import com.sun.net.httpserver.HttpExchange;
import uz.pdp.jaecoder.criteria.UserCriteria;
import uz.pdp.jaecoder.dto.UserDto;
import uz.pdp.jaecoder.service.UserService;
import uz.pdp.jaecoder.dto.BaseResponse;
import uz.pdp.jaecoder.dto.UserCreateDto;
import uz.pdp.jaecoder.dto.UserUpdateDto;
import uz.pdp.jaecoder.domain.User;
import uz.pdp.jaecoder.utils.GsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static uz.pdp.jaecoder.config.ApplicationConfig.GSON;

public class UserController extends AbstractController<UserService> {

    public UserController(UserService service) {
        super(service);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        switch (requestMethod) {
            case "GET" -> doGet(httpExchange);
            case "POST" -> doPost(httpExchange);
            case "PUT" -> doPut(httpExchange);
            case "DELETE" -> doDelete(httpExchange);
            default -> doUnhandled(httpExchange);
        }
    }

    @Override
    protected void doPost(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        InputStream inputStream = httpExchange.getRequestBody();
        UserCreateDto dto = GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), UserCreateDto.class);
        Long id = service.create(dto);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(id.toString().getBytes());
        outputStream.close();
/*        OutputStream outputStream = httpExchange.getResponseBody();
        InputStream inputStream = httpExchange.getRequestBody();
        UserCreateDto userCreateDto = GsonUtil.fromJson(inputStream, UserCreateDto.class);
        User user = userService.create(userCreateDto);
        BaseResponse<User> baseResponse = new BaseResponse<>(user);
        byte[] bytes = GsonUtil.objectToByteArray(baseResponse);
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        outputStream.write(bytes);
        outputStream.close();*/
    }

    @Override
    protected void doPut(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);

/*        OutputStream outputStream = httpExchange.getResponseBody();
        InputStream inputStream = httpExchange.getRequestBody();
        UserUpdateDto userUpdateDto = GsonUtil.fromJson(inputStream, UserUpdateDto.class);
        User user = userService.update(userUpdateDto);
        byte[] bytes = GsonUtil.objectToByteArray(new BaseResponse<>(user));
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        httpExchange.sendResponseHeaders(200, 0);
        outputStream.write(bytes);
        outputStream.close();*/
    }

    @Override
    protected void doDelete(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);

/*        OutputStream outputStream = httpExchange.getResponseBody();
        String uri = getPath(httpExchange);
        Long id = getPathVariableLong(uri);
        userService.deleteById(id);
        BaseResponse<String> baseResponse = new BaseResponse<>("user deleted successfully! ");
        byte[] bytes = GsonUtil.objectToByteArray(baseResponse);
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        outputStream.write(bytes);
        outputStream.close();*/

    }

    @Override
    protected void doGet(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream outputStream = httpExchange.getResponseBody();
        List<UserDto> users = service.getAll(new UserCriteria());
        String responseData = GSON.toJson(users);
        outputStream.write(responseData.getBytes());
        outputStream.close();

        /*        String uri = getPath(httpExchange);
        OutputStream outputStream = httpExchange.getResponseBody();
        Object responseData;
        if (uri.equals("/user")) {
            responseData = userService.getAll();
        } else if (uri.contains("/user/username")) {
            String username = getPathVariableString(uri);
            responseData = userService.getByUsername(username);
        } else if (uri.contains("/user/email")) {
            String email = getPathVariableString(uri);
            responseData = userService.getByEmail(email);
        } else {
            Long id = getPathVariableLong(uri);
            responseData = userService.getById(id);
        }
        BaseResponse<Object> baseResponse = new BaseResponse<>(responseData);
        byte[] bytes = GsonUtil.objectToByteArray(baseResponse);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        httpExchange.sendResponseHeaders(200, 0);
        outputStream.write(bytes);
        outputStream.close();*/
    }


    @Override
    protected void doUnhandled(HttpExchange httpExchange) throws IOException {

/*        OutputStream outputStream = httpExchange.getResponseBody();
        httpExchange.sendResponseHeaders(404, 0);
        httpExchange.getResponseHeaders().add(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        BaseResponse<Void> baseResponse = new BaseResponse<>("not found");
        outputStream.write(GsonUtil.objectToByteArray(baseResponse));
        outputStream.close();*/
    }

//    private static String getPath(HttpExchange httpExchange) throws IOException {
//        return httpExchange.getRequestURI().getPath();
//    }
//
//    private static long getPathVariableLong(String uri) throws IOException {
//        return Long.parseLong(uri.split("/")[2]);
//    }
//
//    private static String getPathVariableString(String uri) throws IOException {
//        return String.valueOf(uri.split("/")[3]);
//    }
}
