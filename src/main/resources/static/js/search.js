Vue.http.options.emulateJSON = true;
//Vue实例
new Vue({
    el: '#app',
    data() {
        return {

            searchInfo: "",
            searchType: '',

            filmInfo:{
            },

            showState:{
                showDouban:false,
                showMaoYan:false,
                showIntegrate:false
            },


            //模糊查询显示状态
            FuzzyQueryState:{
                showDouban:false,
                showMaoYan:false,
                showIntegrate:false
            },

            //豆瓣的模糊查询结果
            movies: [{}],
            HotComments: [{}],
            showingHotComments:false,



            //猫眼的模糊查询结果
            films: [{}],
            filmComments: [{}],
            showingFilmComments:false,


            //集成数据的模糊查询结果
            integrateFilms:[{}],
            commentLists: [{}],
            showIntegrateComments:false,



            activeIndex: '4'

        }
    },
    methods: {

        fuzzyQuery(){

            if(this.searchType==='')
            {
                alert("请选择查询类型");
                return;
            }
            this.closeAllAccurateInfo();
            if(this.searchType==="豆瓣")
                this.fuzzyQueryThroughDouban();
            else if(this.searchType==="猫眼")
                this.fuzzyQueryThroughMaoyan();
            else
                this.fuzzyQueryThroughIntegrate();
        },


        //豆瓣模糊查询
        fuzzyQueryThroughDouban(){
            this.FuzzyQueryState.showDouban = true;
            this.FuzzyQueryState.showMaoYan = false;
            this.FuzzyQueryState.showIntegrate = false;

            this.$http.post('/douban/searchDouBanMovies',{
                searchInfo:this.searchInfo
            }).then(result => {
                console.log(result);
                this.movies = result.body;
            });
        },

        checkDoubanComment(id){
            for(let i=0;i<this.movies.length;i++){
                if(this.movies[i].id===id){
                    this.HotComments = this.movies[i].hotcommentList;
                    break;
                }
            }
            this.showingHotComments=true;

        },

        closeDoubanComments(){
            this.showingHotComments=false;
        },


        //猫眼模糊查询
        fuzzyQueryThroughMaoyan(){
            this.FuzzyQueryState.showDouban = false;
            this.FuzzyQueryState.showMaoYan = true;
            this.FuzzyQueryState.showIntegrate = false;

            this.$http.post('/maoyan/searchMaoyanMovies',{
                searchInfo:this.searchInfo
            }).then(result => {
                console.log(result);
                this.films = result.body;
            });
        },


        checkMaoyanComment(id){
            for(let i=0;i<this.films.length;i++){
                if(this.films[i].id===id){
                    this.filmComments = this.films[i].filmCommentList;
                    break;
                }
            }
            this.showingFilmComments=true;

        },

        closeMaoyanComments(){
            this.showingFilmComments=false;
        },



        fuzzyQueryThroughIntegrate(){
            this.FuzzyQueryState.showDouban = false;
            this.FuzzyQueryState.showMaoYan = false;
            this.FuzzyQueryState.showIntegrate = true;



            this.$http.post('/integrate/searchIntegratedFilm',{
                searchInfo:this.searchInfo
            }).then(result => {
                console.log(result);
                this.integrateFilms = result.body;
            });

        },

        checkIntegrateComment(integrateFilm){
            console.log(integrateFilm)
            this.commentLists = integrateFilm.commentList;
            this.showIntegrateComments=true;
        },

        closeIntegrateComment(){
            this.showIntegrateComments=false;
        },



        closeAllAccurateInfo(){
            this.showState.showDouban = false;
            this.showState.showMaoYan = false;
            this.showState.showIntegrate = false;
        },


        //精确查询
        searchFilmInfo(){
            this.closeAllFuzzyQuery();
            if(this.searchType==='')
            {
                alert("请选择查询类型");
                return;
            }
            console.log(this.searchInfo+" "+this.searchType);
            if(this.searchType==="豆瓣")
                this.searchThroughDouban();
            else if(this.searchType==="猫眼")
                this.searchThroughMaoYan();
            else
                this.searchThroughIntegrate();

        },

        closeAllFuzzyQuery(){
            this.FuzzyQueryState.showDouban = false;
            this.FuzzyQueryState.showMaoYan = false;
            this.FuzzyQueryState.showIntegrate = false;
        },

        searchThroughDouban(){
            if(this.searchInfo===""){
                alert("请输入搜索信息");
                return;
            }

            this.closeAllFuzzyQuery();
            this.showState.showDouban = true;
            this.showState.showMaoYan = false;
            this.showState.showIntegrate = false;

            this.$http.post('/douban/findDouBanDataByMovieName',{
                searchInfo:this.searchInfo,
            }).then(result => {
                console.log(result);
                this.filmInfo = result.body;
            });

        },

        searchThroughMaoYan(){
            if(this.searchInfo===""){
                alert("请输入搜索信息");
                return;
            }

            this.showState.showDouban = false;
            this.showState.showMaoYan = true;
            this.showState.showIntegrate = false;

            this.$http.post('/maoyan/findMaoYanDataByName',{
                searchInfo:this.searchInfo,
            }).then(result => {
                console.log(result);
                this.filmInfo = result.body;
            });


        },

        searchThroughIntegrate(){
            this.showState.showDouban = false;
            this.showState.showMaoYan = false;
            this.showState.showIntegrate = true;

            this.$http.post('/integrate/findIntegratedFilmByName',{
                searchInfo:this.searchInfo,
            }).then(result => {
                console.log(result);
                this.filmInfo = result.body;
            });
        }

    },


    //声明周期钩子函数-->在data和methods渲染结束后执行
    created() {

    }



});