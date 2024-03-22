# Git学习

## 分区及流程

### git分区

- 工作区
  工作区（Workspace）即本地代码所在的目录，同时也是存放 .git/ （本地仓库）的目录。

- 暂存区
  暂存区（Index/Stage）是工作区和本地仓库的缓存空间，里面记录着即将提交给本地仓库（版本库）的文件修改信息，.git/ 目录里的index文件就是暂存区。

- 本地仓库
  本地仓库（Repository）也称本地库或版本库，存放了本地的所有版本（commit提交记录），本地仓库的文件都在 .git/ 目录中。

- 远程仓库
  远程仓库（Remote）在网络上，GitHub、Gitee和GitLab都能创建远程仓库，和本地仓库一样，远程仓库存放的也是不同的代码版本，只是这些版本可以来自多个本地仓库。

### 工作流程

---



![image-20240318223148175](C:\Users\fhkin\AppData\Roaming\Typora\typora-user-images\image-20240318223148175.png)

---



![image-20240318223444904](C:\Users\fhkin\AppData\Roaming\Typora\typora-user-images\image-20240318223444904.png)

## 本地仓库的初始化

指令：git init

## Git常见命令

### 基本指令

- git add "file":将文件添加到缓存 
- git status ：查看文件的状态
- git diff ：
- git commit
- git commit -m ' 添加你的说明'
- git reset HEAD
- git rm 
- git mv
- dir 查看文件夹
- git log 查看提交历史

详细说明：

#### git rm

如果只是简单地从工作目录中手工删除文件，运行 git status 时就会在 Changes not staged for commit 的提示。要从 Git 中移除某个文件，就必须要从已跟踪文件清单中移除，然后提交。就可以使用

- git rm 'file'

如果删除之前修改过并且已经放到暂存区域的话，则必须要用强制删除选项 -f

- git rm -f 'file'

 如果把文件从暂存区域移除，但仍然希望保留在当前工作目录中，换句话说，仅是从跟踪清单中删除，使用 --cached 选项即可

- git rm --cached 'file'

### Git 分支管理

- **git branch**：查看分支命令
- **git branch (branchname)**：创建分支命令
- **git branch -a  ：查看当前分支**
- **git checkout (branchname)**：切换分支命令
- **git merge**：合并分支命令(建议先切换到主分支上)
- **git branch -d (branchname)**：删除分支命令

### Git远程仓库  

- **git remote add**：添加远程仓库
- **git remote**：查看当前的远程仓库
- **git fetch**、**git pull**：提取远程仓仓库
- **git push**：推送到远程仓库
- **git remote rm**：删除远程仓库（断除联系，不是物理上的删除）

注意：如果push的话，得先与远程仓库取得联系：

```
git remote add  origin git@github.com:（你的名称）/仓库名.git   你的ssh
```

### 删除（修改）github上仓库的文件

- 方法一： 可以先clone文件在本地仓库进行修改后再push
- 方法二 ：pull下来后再对文件修改，push后在github产生一个新分支，可以在github上合并

## Git合并冲突的情况

![image-20240322225530315](C:\Users\fhkin\AppData\Roaming\Typora\typora-user-images\image-20240322225530315.png)