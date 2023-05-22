import { createRouter, createWebHashHistory } from 'vue-router'
import AdminLayout from "@/layout/AdminLayout";
import TeacherLayout from "@/layout/TeacherLayout";
import Login from "@/views/Login";

const routes = [
  //默认
  {
    path: '/',
    name: 'index',
    component: Login,
  },
    // 教师
  {
    path: '/teacher',
    name: 'teacherLayout',
    component: TeacherLayout,
    redirect: '/dashboard',
    children:[
      {
        path: '/dashboard',
        name: 'dashboard',
        component: () => import('@/views/teacher/Dashboard.vue'),
      },
      {
        path: '/teacherQueryQuestion',
        name: 'teacherQueryQuestion',
        component:  () => import('@/views/teacher/TeacherQueryQuestion.vue')
      },
      {
        path: '/teacherAddQuestion',
        name: 'teacherAddQuestion',
        component:  () => import('@/views/teacher/TeacherAddQuestion.vue')
      },
      {
        path: '/teacherQuestionBank',
        name: 'teacherQuestionBank',
        component:  () => import('@/views/teacher/TeacherQuestionBank.vue')
      },
      {
        path: '/teacherCourse',
        name: 'teacherCourse',
        component:  () => import('@/views/teacher/TeacherCourse.vue')
      },
      {
        path: '/teacherPaper',
        name: 'teacherPaper',
        component:  () => import('@/views/teacher/TeacherPaper.vue')
      },

      {
        path: '/autoPaper',
        name: 'autoPaper',
        component:  () => import('@/views/teacher/AutoPaper.vue')
      },
      {
        path: '/handPaper',
        name: 'handPaper',
        component:  () => import('@/views/teacher/HandPaper.vue')
      },
      {
        path: '/teacherAddBatch',
        name: 'teacherAddBatch',
        component:  () => import('@/views/teacher/TeacherAddBatch.vue')
      },
      {
        path: '/template',
        name: 'template',
        component:  () => import('@/views/teacher/PaperTemplate.vue')
      },
      {
        path: '/point',
        name: 'point',
        component:  () => import('@/views/teacher/Point.vue')
      },
      {
        path: '/addPaper',
        name: 'addPaper',
        component:  () => import('@/views/teacher/AddPaper.vue')
      },
      {
        path: '/personalInfo',
        name: 'personalInfo',
        component:  () => import('@/views/teacher/PersonalInfo.vue')
      },
      {
        path: '/lookPaper',
        name: 'lookPaper',
        component:  () => import('@/views/teacher/LookPaper.vue')
      },
      {
        path: '/analysePaper',
        name: 'analysePaper',
        component:  () => import('@/views/teacher/AnalysePaper.vue')
      }
   ]
  },
    //管理员
  {
    path: '/admin',
    name: 'adminLayout',
    component: AdminLayout,
    // redirect: "/home",
    children:[
      {
        path: '/user',
        name: 'user',
        component:  () => import('@/views/admin/User.vue')
      },
      {
        path: '/logs',
        name: 'logs',
        component:  () => import('@/views/admin/Logs.vue')
      },
      {
        path: '/parameters',
        name: 'parameters',
        component:  () => import('@/views/admin/Parameters.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('@/views/teacher/Test.vue')
  },
  {
    path: '/home',
    name: 'home',
    component:  () => import('@/views/Home.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
