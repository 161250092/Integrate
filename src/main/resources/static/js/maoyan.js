Vue.http.options.emulateJSON = true;
//Vue实例
new Vue({
    el: '#app',
    data() {
        return {
            films: [{}],

            pageConf: {
                //设置一些初始值(会被覆盖)
                pageCode: 1, //当前页
                pageSize: 10, //每页显示的记录数
                totalPage: 12, //总记录数
                pageOption: [10, 15, 20], //分页选项
            },

            //对话框中展示的热评
            filmComments: [{}],

            showingFilmComments:false,


            activeIndex: '2'

        }
    },
    methods: {

        search(pageCode, pageSize) {
            this.$http.post('/maoyan/findmaoyanDataByConPage',{
                pageSize:pageSize,
                pageCode:pageCode
            }).then(result => {
                console.log(result);
                this.films = result.body.rows;
                this.pageConf.totalPage = result.body.total;
            });

        },

        //调整页面展示数量
        handleSizeChange(val) {
            this.pageConf.pageSize = val;
            this.search(this.pageConf.pageCode, val);
        },

        //换页
        handleCurrentChange(val) {
            this.pageConf.pageCode = val; //为了保证刷新列表后页面还是在当前页，而不是跳转到第一页
            this.search(val, this.pageConf.pageSize);
        },


        checkHotComment(id){
            for(let i=0;i<this.films.length;i++){
                if(this.films[i].id===id){
                    this.filmComments = this.films[i].filmCommentList;
                    break;
                }
            }
            this.showingFilmComments=true;

        },

        closeComments(){
            this.showingFilmComments=false;
        }


    },


    //声明周期钩子函数-->在data和methods渲染结束后执行
    created() {
        this.search(this.pageConf.pageCode, this.pageConf.pageSize);
    }



});