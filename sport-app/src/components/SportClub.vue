<template>
  <div class="container">
    <div class="row">
      <div class="col-md-8">
        <input type="text" v-model="search" class="form-control" placeholder="Пошук">
      </div>
      <div class="col-md-4">
        <button type="button" @click="showModal = true" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter">
          Додати
        </button>
      </div>
    </div>
    <br>

    <b-card  v-for="sportClub in filteredList" :key="sportClub.sportClubId" v-bind:data="sportClub" :title="sportClub.name"
             img-src="../static/img/sport-club.jpg"
             img-alt="Image"
             img-top
             tag="article"
             style="max-width: 20rem;"
             class="mb-2">
      <p class="card-text">
        <p>Дата заснування :... {{sportClub.foundationDate}}</p>
      </p>
      <b-button variant="success" @click="showUpdateModals(sportClub)">Змінити</b-button>
      <b-button @click="deleteSportClub(sportClub.sportClubId)" variant="danger">Видалити</b-button>
    </b-card>

    <!-- Modal -->
    <div v-if="showModal">
      <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-dialog sss">
              <div class="modal-content">
                <div class="modal-header">
                  Додати
                  <button type="button" class="close" @click="showModal=false">
                    <span aria-hidden="true">&times;</span>
                  </button>

                </div>
                <div class="modal-body">

                  <div class="form-group">
                    <label>Назва</label>
                    <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                  </div>

                  <div class="form-group">
                    <label>Дата заснування</label>
                    <input v-model="formAdd.foundationDate" type="date" class="form-control" >
                  </div>

                  <button class="btn btn-warning" @click="showModal=false">Скасувати</button>
                  <button v-if="formAdd.name !== '' && formAdd.foundationDate !== undefined"
                          class="btn btn-success" @click="addSportClub">Зберегти</button>
                  <button v-else class="btn btn-success" disabled>Зберегти</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>

<div v-if="showUpdateModal">
      <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-dialog sss">
              <div class="modal-content">
                <div class="modal-header">
                  Додати
                  <button type="button" class="close" @click="showUpdateModal=false">
                    <span aria-hidden="true">&times;</span>
                  </button>

                </div>
                <div class="modal-body">

                  <div class="form-group">
                    <label>Назва</label>
                    <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                  </div>

                  <div class="form-group">
                    <label>Дата заснування</label>
                    <input v-model="formAdd.foundationDate" type="date" class="form-control" >
                  </div>

                  <button class="btn btn-warning" @click="showUpdateModal=false">Скасувати</button>
                  <button v-if="formAdd.name !== '' && formAdd.foundationDate !== undefined"
                          class="btn btn-success" @click="updateSportClub(formAdd)">Зберегти</button>
                  <button v-else class="btn btn-success" disabled>Зберегти</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>


  </div>
</template>

<script>
  import axios from 'axios'
    export default {
        name: "SportClub",
      data: () => ({
        search: '',
        sportClubs: [],
        formAdd: {},
        showModal: false,
        showUpdateModal: false
      }),
      created() {
          this.fetchSportClubs();
      },
      methods: {
        fetchSportClubs() {
          axios.get('/api/sport-clubs').then((response) => {
            this.sportClubs = response.data;
          });
        },
        deleteSportClub(id) {
          axios.delete('/api/sport-clubs/' + id).then(() => {
            this.fetchSportClubs();
          });
        },
        addSportClub() {
          this.showModal = false;
          axios.post('/api/sport-clubs/', this.formAdd).then(() => {
            this.fetchSportClubs();
          });
          this.formAdd = {};
        },
        updateSportClub(sportClub) {
          this.showUpdateModal = false;
          axios.put('/api/sport-clubs/' + sportClub.sportClubId, sportClub).then(() => {
            this.fetchSportClubs();
          });
          this.formAdd = {};
        },
        showUpdateModals(sportClub) {
          this.showUpdateModal = true;
          this.formAdd = sportClub;
        },
      },
      computed: {
        filteredList() {
          return this.sportClubs.filter(sportClub => {
            return sportClub.name.toLowerCase().includes(this.search.toLowerCase());
          })
        }
      }
    }
</script>

<style scoped>
  .container {
    margin-top: 20px;
  }
  .card {
    display: inline-block;
    margin: 8px;
  }

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
    overflow-y: scroll;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }
</style>
