<template>
  <div id="app">

    <b-navbar toggleable="md" type="dark" variant="dark">

      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>

      <b-navbar-brand href="#">Спорт</b-navbar-brand>

      <b-collapse is-nav id="nav_collapse">

        <b-navbar-nav>
          <b-nav-item to="/sportsman">Спортсмени</b-nav-item>
          <b-nav-item to="/coach">Тренери</b-nav-item>
          <b-nav-item to="/sport-club">Спортивні клуби</b-nav-item>
          <b-nav-item to="/organizer">Організатори</b-nav-item>
          <b-nav-item to="/sport-kind">Види спорту</b-nav-item>
          <b-nav-item to="/competition">Змагання</b-nav-item>
          <b-nav-item to="/stadium">Стадіони</b-nav-item>
          <b-nav-item to="/court">Тенісні корти</b-nav-item>
          <b-nav-item to="/gym">Спортзали</b-nav-item>
        </b-navbar-nav>

      </b-collapse>
    </b-navbar>

    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>

</style>
