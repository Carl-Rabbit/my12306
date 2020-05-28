## 4.23

### 1. First level
_**Finish first, basic require**_

#### 1.1 User part

1. create/alter/drop user
1. get user
    by name / by id
1. create/alter/drop passenger
1. search all tickets in account.
1. buy one ticket
1. endorse one ticket
1. refund one ticket

#### 1.2 Admin part

1. create/alter/drop admin
1. get admin
    by name / by id
1. arrange one train route
    add all, add one, remove all, remove one

#### 1.3 Common part

1. check role
1. pick one route
    train, day => train info
1. select routes
    day, begin, end => routes, left ticket.

### 2. Second level
_**Try to finish all**_

1. take order for one person (buy multi tickets)
1. take order for multi passenger in one trip (buy multi tickets)
1. create/alter/drop train (seats)
1. create/alter/drop station
1. create/alter/drop city

### 3. Third level
_**Try when finish first two level**_
_**Ordered by importance**_

1. seat choosing
1. route recommand (station-transfer route)
1. student ticket online fill-up

***



## 5.15

### 1. user operation

<table><thead><tr><th>Function</th><th>Type</th><th>Interface</th><th>Param</th><th>Auth</th><th>Comment</th></tr></thead><tbody><tr><td rowspan="2">get info</td><td rowspan="2">GET</td><td>/user</td><td rowspan="2"><span style="font-weight:400;font-style:normal">?id=ID&amp;name=NAME</span><br></td><td>user</td><td rowspan="2"><span style="font-weight:400;font-style:normal">Require at least</span><br><span style="font-weight:400;font-style:normal">one param</span><br></td></tr><tr><td>/admin/user</td><td>admin</td></tr><tr><td>register</td><td>POST</td><td>/user</td><td>Json: user info</td><td></td><td></td></tr><tr><td>update</td><td>PUT</td><td>/user</td><td>Json: user info</td><td>user</td><td></td></tr><tr><td>delete</td><td>DELETE</td><td>/admin/user</td><td>?id=ID&amp;name=NAME</td><td>admin</td><td></td></tr><tr><td>get all</td><td>GET</td><td>/admin/users</td><td></td><td>admin</td><td></td></tr><tr><td>count</td><td>GET</td><td>/admin/users/num</td><td></td><td>admin</td><td></td></tr></tbody></table>

### 2. passenger operation

<table><thead><tr><th>Function</th><th>Type</th><th>Interface</th><th>Extra Param</th><th>Auth</th><th>Comment</th></tr></thead><tbody><tr><td rowspan="2">get info</td><td rowspan="2">GET</td><td>/user/passenger</td><td rowspan="2">/{psg_id}<br>or<br><span style="font-weight:400;font-style:normal">?id=ID&amp;name=NAME</span><br></td><td>user</td><td rowspan="2">Get one<br>or all</td></tr><tr><td>/admin/user/passenger</td><td>admin</td></tr><tr><td>add</td><td>POST</td><td>/user/{id}/passenger</td><td>Json: passenger info</td><td>user</td><td></td></tr><tr><td>update</td><td>PUT</td><td><span style="font-weight:400;font-style:normal">/user/{id}/passenger/{psg_id}</span></td><td>Json: passenger info</td><td>user</td><td></td></tr><tr><td rowspan="2">delete</td><td rowspan="2">DELETE</td><td><span style="font-weight:400;font-style:normal">/user/{id}/passenger</span><br></td><td>/{psg_id}</td><td>user</td><td>Set disable</td></tr><tr><td>/admin/<span style="font-weight:400;font-style:normal">user/{id}/passenger</span></td><td>/{psg_id}?true=TRUE</td><td>admin</td><td>Set disable<br>or true delete</td></tr><tr><td>get all</td><td>GET</td><td>/admin/passengers</td><td></td><td>admin</td><td></td></tr><tr><td>count</td><td>GET</td><td>/admin/passengers/num</td><td></td><td>admin</td><td></td></tr></tbody></table>

### 3. order operation
