# Basic Quiz 后端需求说明

开发一个 Spring Boot Web Application，提供 API 供前端 app 调用，以显示完整的个人简历信息。

该 app 无需使用任何 Database 或其它 Data Store 来存储应用数据，包括本地文件，所有数据均被保存在内存中，请自行组织代码保存相应的应用数据。

请统一使用默认的 8080 端口，无需使用 HTTPS。

无需编写任何自动化测试。

无需严格按照 RESTful API 要求进行实现。如果你还不了解 REST，那么请直接忽略这一条。如果你已经了解了 REST，请尽量按照 REST 提倡的实践来实现以下 API。

在最终提交前，请保证使用`curl`或`Postman`充分测试通过。

## API 列表

### 查询个人基本信息

#### ENDPOINT

GET /users/:id

#### REQUEST

`id`: 个人 ID。

#### RESPONSE

查询成功会返回 User 对象：

| 字段:类型          | 说明               |
| ------------------ | ------------------ |
| id:long            | ID。               |
| name:string        | 名字。             |
| age:long           | 年龄。             |
| avatar:string      | 头像图片链接地址。 |
| description:string | 个人介绍信息。     |

#### EXAMPLE

```shell
$ curl localhost:8080/users/1
{
    "id": 1,
    "name": "KAMIL",
    "age": 24,
    "avatar": "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
    "description": "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi."
}
```

### 查询个人教育经历列表

#### ENDPOINT

GET /users/:id/educations

#### REQUEST

`id`: 个人 ID。

#### RESPONSE

查询成功会返回包含 Education 对象的 list，Education 对象字段如下：

| 字段:类型          | 说明           |
| :----------------- | :------------- |
| userId:long        | 用户 ID。      |
| year:long          | 历年。         |
| title:string       | 教育经历标题。 |
| description:string | 教育经历描述。 |

#### EXAMPLE

```shell
$ curl localhost:8080/users/1/educations
[
   {
      "description" : "Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.",
      "year" : "2005",
      "title" : "Secondary school specializing in artistic",
      "userId" : 1
   },
   {
      "description" : "Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.",
      "year" : "2009",
      "title" : "First level graduation in Graphic Design",
      "userId" : 1
   }
]
```

### 创建新的个人基本信息

#### ENDPOINT

POST /users

#### REQUEST

提供 User 对象的以下字段：

| 字段:类型          | 是否必需 | 说明                                        |
| :----------------- | -------- | :------------------------------------------ |
| name:string        | 是       | 名字，可以重复。长度范围：1 - 128 bytes。   |
| age:long           | 是       | 年龄。取值范围：> 16。                      |
| avatar:string      | 是       | 头像图片链接地址。长度范围：8 - 512 bytes。 |
| description:string | 否       | 个人介绍信息。长度范围：0 - 1024 bytes。    |

#### RESPONSE

除对应的 HTTP Status Code 外，还需要返回系统为新创建的 user 生成的 ID。

注：如需按照 RESTful API 实现，无需受上一句要求限制，请自行按 RESTful API 要求实现即可。

#### EXAMPLE

```shell
$ cat user.json
{
    "name": "Panda",
    "age": 28,
    "avatar": "https://i.dlpng.com/static/png/6681915_preview.png",
    "description": "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..."
}
$ curl -H "Content-Type: application/json" --data @user.json localhost:8080/users
10
$ curl -v -H "Content-Type: application/json" --data @user.json localhost:8080/users
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST /users HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 244
>
* upload completely sent off: 244 out of 244 bytes
< HTTP/1.1 201
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 17 Jun 2020 05:33:29 GMT
<
* Connection #0 to host localhost left intact
11* Closing connection 0
```

### 增加新的个人教育经历

#### ENDPOINT

POST /users/:user_id/educations

#### REQUEST

提供 Education 对象的以下字段：

| 字段:类型          | 是否必需 | 说明                                     |
| :----------------- | :------- | :--------------------------------------- |
| year:long          | 是       | 历年。                                   |
| title:string       | 是       | 教育经历标题。长度范围：1 - 256 bytes。  |
| description:string | 是       | 教育经历描述。长度范围：1 - 4096 bytes。 |

#### RESPONSE

除对应的 HTTP Status Code 外，无需要返回其它信息。

注：如需按照 RESTful API 实现，无需受上一句要求限制，请自行按 RESTful API 要求实现即可。

#### EXAMPLE

```shell
$ cat education.json
{
    "description" : "Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.",
    "year" : "2005",
    "title" : "Secondary school specializing in artistic",
    "userId" : 1
}
$ curl -v -H "Content-Type: application/json" --data @education.json localhost:8080/users/1/educations
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST /users/1/educations HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 221
>
* upload completely sent off: 221 out of 221 bytes
< HTTP/1.1 201
< Content-Length: 0
< Date: Wed, 17 Jun 2020 05:55:18 GMT
<
* Connection #0 to host localhost left intact
* Closing connection 0
```



## 返回结果说明

使用标准 HTTP Status Code 来标识返回结果成功或失败与否。具体可以使用以下 status code，如需额外添加，请自行判断并保证合理使用。

| HTTP Status Code    | Summary                                                  |
| ------------------- | -------------------------------------------------------- |
| 200 - OK            | 查询操作一切正常，返回 200 及查询结果。                  |
| 201 - Created       | 创建操作成功，返回 201。                                 |
| 400 - Bad Request   | 请求参数不符合要求，通常是因为参数格式不正确或参数缺失。 |
| 404 - Not Found     | 请求的资源不存在。                                       |
| 500 - Server Errors | 请求                                                     |

除了返回对应的 status code 外，对于出错的情况，还需返回 Error 对象，字段如下：

| 字段:类型        | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| timestamp:string | 错误发生的时间的时间戳。示例：2020-06-16T08:16:57.549+00:00  |
| status:integer   | 对应的 HTTP Status Code。如：404                             |
| error:string     | 对应的 HTTP Status Code 描述。如：Not Found                  |
| message:string   | 额外的错误提示信息，内容可以自行编写，表意即可。如：Cannot find basic info of person with id is 666. |

一个示例如下：

```json
curl -v localhost:8080/users/666
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /users/666 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 404
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 17 Jun 2020 05:04:07 GMT
<
{
   "timestamp" : "2020-06-17T05:04:07.641+00:00",
   "error" : "Not Found",
   "status" : 404,
   "message" : "Cannot find basic info for user with id 666."
}
```

