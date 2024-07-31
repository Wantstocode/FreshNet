const search = () => {
    let query = $("#search-input").val();

    if(query==''){
        $(".search-result").hide();
    }
    else{
        console.log(query);

        //sending request to backend
        let url='http://localhost:8080/shop/search/${query}';
        fetch(url)
            .then((response) => {
              return response.json();
            })
            .then((data)=>{
            console.log(data);

//            let text = "<div class='list-group'>";
//            data.forEach((product)=>{
//                text += "<a th:href='#' class='list-group-item list-group-item-action'>${product.name} </a>";
//            });
//            text += '</div>';
//            $(".search-result").html(text);
            $(".search.result").show();
            });
    }
};