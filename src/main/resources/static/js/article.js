const deleteButton = document.getElementById('delete-btn');
const updateButton = document.getElementById('modify-btn');

if(deleteButton){
    deleteButton.addEventListener('click',event =>{
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`,{
            method: 'DELETE'
        })
        .then(()=>{
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        });
    });
}

if(updateButton){
    updateButton.addEventListener('click', event =>{
       let params = new URLSearchParams(location.search);
       let id = params.get('id');

       fetch(`/api/articles/${id}`,{
        method:'PUT',
        headers:{
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        })
       })
       .then(()=>{
            alert('수정이 완료되었습니다.');
            location.replace(`/articles/${id}`);
       })


    })
}

// fetch(url, options) : 컨트롤러를 호출하고 option에는 get/post/delete/put 등의 방법을 쓰면 그대로 호출
// ``을 사용한 이유: 변수를 넣기 위해서