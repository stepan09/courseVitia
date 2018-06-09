<template>
  <div class="container">
    <div class="row">
      <div class="col-md-8">
        <input type="text" v-model="search" class="form-control" placeholder="Пошук">
      </div>
      <div class="col-md-1">
        <button type="button" @click="showModal = true" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter">
           Додати
        </button>
      </div>
      <div class="col-md-1">
        <button class="btn btn-outline-success" @click="fetchCoaches">Усі</button>
      </div>
    </div>
    <br>
    <b-tabs pills card>
      <b-tab title="Запит 6" active>
        <br>
        <p>Тренери за спортсменом</p>
        <div class="row">
          <div class="col-md-3">
            <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                           v-model="sportsmanId"
                           placeholder="Спортсмени">
              <option v-for="sportsman in sportsmen" :value="sportsman.sportsmanId">
                {{ sportsman.lastName}} {{sportsman.firstName}}
              </option>
            </b-form-select>
          </div>
          <div class="col-md-1">
            <button class="btn btn-info" @click="getCoachesBySportsmanId(sportsmanId)">Пошук</button>
          </div>
        </div>
      </b-tab>
      <b-tab title="Усі" @click="fetchCoaches" active>
        <br>
      </b-tab>
    </b-tabs>
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
                    <label>Прізвище</label>
                    <input v-model="formAdd.lastName" type="text" class="form-control" placeholder="Введіть прізвище">
                  </div>

                  <div class="form-group">
                    <label>Ім'я</label>
                    <input v-model="formAdd.firstName" class="form-control" placeholder="Введіть ім'я">
                  </div>

                  <div class="form-group">
                    <label>По батькові</label>
                    <input v-model="formAdd.middleName" class="form-control" placeholder="Введіть по батькові">
                  </div>

                  <div class="form-group">
                    <label>Дата народження</label>
                    <input v-model="formAdd.birthDate" type="date" class="form-control" >
                  </div>

                  <div class="form-group">
                    <label class="my-1 mr-2">Спортсмени</label>
                    <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportsmen" multiple>
                      <option v-for="sportsman in sportsmen" :value="sportsman">
                        {{sportsman.lastName}} {{sportsman.firstName}}
                      </option>
                    </select>
                  </div>
                  <button class="btn btn-warning" @click="showModal=false">Скасувати</button>
                  <button v-if="formAdd.lastName !== undefined && formAdd.firstName !== undefined && formAdd.middleName
                   !== undefined && formAdd.birthDate !== undefined"
                          class="btn btn-success" @click="addCoach">Зберегти</button>

                  <button v-else class="btn btn-success" @click="addCoach" disabled>Зберегти</button>

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
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" @click="showUpdateModal=false">
                    <span aria-hidden="true">&times;</span>
                  </button>

                </div>
                <div class="modal-body">

                  <div class="form-group">
                    <label>Прізвище</label>
                    <input v-model="formAdd.lastName" type="text" class="form-control" aria-describedby="emailHelp" placeholder="Enter email">
                  </div>

                  <div class="form-group">
                    <label>Ім'я</label>
                    <input v-model="formAdd.firstName" type="text" class="form-control" aria-describedby="emailHelp" placeholder="Enter email">
                  </div>

                  <div class="form-group">
                    <label>По батькові</label>
                    <input v-model="formAdd.middleName" type="text" class="form-control" aria-describedby="emailHelp" placeholder="Enter email">
                  </div>

                  <div class="form-group">
                    <label>Дата народження</label>
                    <input v-model="formAdd.birthDate" type="date" class="form-control"  aria-describedby="emailHelp" placeholder="Enter email">
                  </div>

                  <div class="form-group">
                    <label class="my-1 mr-2">Спортсмени</label>
                    <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportsmen" multiple>
                      <option v-for="sportsman in sportsmen" :value="sportsman">
                        {{sportsman.lastName}} {{sportsman.firstName}}
                      </option>
                    </select>
                  </div>
                  <button class="btn btn-warning" @click="showUpdateModal=false">Скасувати</button>
                  <button v-if="formAdd.lastName !== undefined && formAdd.firstName !== undefined && formAdd.middleName
                   !== undefined && formAdd.birthDate !== undefined"
                          class="btn btn-success" @click="updateCoach(formAdd)">Зберегти</button>

                  <button v-else class="btn btn-success" disabled>Зберегти</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </transition>
    </div>

    <b-card  v-for="coach in filteredList" :key="coach.coachId" v-bind:data="coach" :title="coach.lastName + ' ' + coach.firstName"
            img-src="../static/img/coach.jpg"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 20rem;"
            class="mb-2">
      <p class="card-text">
        <p>Дата народж. {{ coach.birthDate }}</p>
      <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                     :value="null"
                     id="inlineFormCustomSelectPref" placeholder="Спортсмени">
        <option disabled slot="first" :value="null">Спортсмени</option>
        <option v-for="sportsman in coach.sportsmen">{{ sportsman.lastName}} {{sportsman.firstName}}</option>
      </b-form-select>
      </p>
      <b-button variant="success" @click="showUpdateModals(coach)">Змінити</b-button>
      <b-button @click="deleteCoach(coach.coachId)" variant="danger">Видалити</b-button>
    </b-card>
  </div>
</template>

<script>
  import axios from 'axios'
  export  default {
    name: 'Coach',
    data: () => ({
      search: '',
      coaches: [],
      sportsmen: [],
      sportsmanId: {},
      showModal: false,
      showUpdateModal: false,
      formAdd: {},
    }),
    created() {
      this.fetchCoaches();
      this.fetchSportsmen();
    },
    methods: {
      fetchCoaches() {
        axios.get('/api/coaches').then((response) => {
          this.coaches = response.data;
        })
      },
      fetchSportsmen() {
        axios.get('/api/sportsmen').then((response) => {
          this.sportsmen = response.data;
        })
      },
      getCoachesBySportsmanId(sportsmanId) {
        axios.get('/api/coaches-by-sportsman/' + sportsmanId).then((response) => {
          this.coaches = response.data;
        })
      },
      deleteCoach(id) {
        axios.delete('/api/coaches/' + id).then(() => {
          this.fetchCoaches();
        })
      },
      addCoach() {
        console.log(this.formAdd.lastName);
        this.showModal = false;
        axios.post('/api/coaches/', this.formAdd).then(() => {
          this.fetchCoaches();
        });
        this.formAdd = {};
      },
      updateCoach(coach) {
        this.showUpdateModal = false;
        axios.put('/api/coaches/' + coach.coachId, coach).then(() => {
          this.fetchCoaches();
        });
        this.formAdd = {};
      },
      showUpdateModals(coach) {
        this.showUpdateModal = true;
        this.formAdd = coach;
      }
    },
    computed: {
      filteredList() {
        return this.coaches.filter(coach => {
          return coach.lastName.toLowerCase().includes(this.search.toLowerCase())
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
    position: absolute;
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

  #mar {
    margin-top: 10px;
  }



</style>
