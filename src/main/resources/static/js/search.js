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

            activeIndex: '4'

        }
    },
    methods: {

        searchFilmInfo(){
            console.log(this.searchInfo+" "+this.searchType);
            if(this.searchType==="豆瓣")
                this.searchThroughDouban();
            else if(this.searchType==="猫眼")
                this.searchThroughMaoYan();
            else
                this.searchThroughIntegrate();

        },

        searchThroughDouban(){
            if(this.searchInfo===""){
                alert("请输入搜索信息");
                return;
            }

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

        }

    },


    //声明周期钩子函数-->在data和methods渲染结束后执行
    created() {

    }



});