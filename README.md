#  一·添加 gitee 和 github的公钥
 具体操作：./git/gitOperation.md
#  二·添加 gitee 和 github

1、首先关联Github和Gitee的远程仓库；

```swift
git remote add github git@github.com:IThawk/learnCode.git
git remote add gitee git@gitee.com:IThawk/learnCode.git
```

2、然后打开“.git”文件夹内的配置文件；接着修改remote改两个，一个Github库另一个Gitee库；

```text
[core]
  repositoryformatversion = 0
  filemode = true
  bare = false
  logallrefupdates = true
[remote "origin"]
  url = git@github.com:IThawk/learnCode.git
  fetch = +refs/heads/*:refs/remotes/github/*
[branch "master"]
  remote = origin
  merge = refs/heads/master
```

将上述文件内容[remote "origin"]内容复制，修改origin名称，内容如下：

```text
[core]
  repositoryformatversion = 0
  filemode = true
  bare = false
  logallrefupdates = true
[remote "github"]
  url = git@github.com:IThawk/learnCode.git
  fetch = +refs/heads/*:refs/remotes/github/*
[remote "gitee"]
  url = git@gitee.com:IThawk/learnCode.git
  fetch = +refs/heads/*:refs/remotes/gitee/*
[branch "master"]
  remote = origin
  merge = refs/heads/master
```

3、代码添加：
```text
git add .
```
4、代码提交：
```text
git commit .
```

5、代码分别Github和Gitee即可。

提交到github

```text
git pull github master
```

5、代码分别提交到Github和Gitee即可。

提交到github

```text
git push github master
```

提交到码云

```text
git push gitee master
```

 
mongodb 客户端