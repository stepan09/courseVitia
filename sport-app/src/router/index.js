import Vue from 'vue'
import Router from 'vue-router'
import Coach from '../components/Coach'
import Sportsman from '../components/Sportsman'
import Competition from '../components/Competition'
import Gym from '../components/Gym'
import Organizer from '../components/Organizer'
import SportKind from '../components/SportKind'
import SportClub from '../components/SportClub'
import Stadium from '../components/Stadium'
import Court from '../components/Court'

Vue.use(Router);

export default new Router({
  routes: [
    {path: '/coach', name: 'Coach', component: Coach},
    {path: '/sportsman', name: 'Sportsman', component: Sportsman},
    {path: '/competition', name: 'Competition', component: Competition},
    {path: '/gym', name: 'Gym', component: Gym},
    {path: '/organizer', name: 'Organizer', component: Organizer},
    {path: '/sport-kind', name: 'SportKind', component: SportKind},
    {path: '/sport-club', name: 'SportClub', component: SportClub},
    {path: '/stadium', name: 'Stadium', component: Stadium},
    {path: '/court', name: 'Court', component: Court},
  ]
})
