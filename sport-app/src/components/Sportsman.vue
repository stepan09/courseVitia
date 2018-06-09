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
        <button class="btn btn-outline-success" @click="fetchSportsmen">Усі</button>
      </div>
    </div>
    <br>
    <b-tabs pills card>
      <b-tab title="Запит 1" active>
        <br> Спортсмени за тренером
        <div class="row">
          <div class="col-md-3">
            <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                           v-model="coachId"
                           placeholder="Тренери">
              <option disabled slot="first" :value="null">Тренери</option>
              <option v-for="coach in coaches" :value="coach.coachId">
                {{ coach.lastName}} {{coach.firstName}}
              </option>
            </b-form-select>
          </div>
          <div class="col-md-1">
            <button class="btn btn-info" @click="getSportsmenByCoachId(coachId)">Пошук</button>
          </div>
        </div>
      </b-tab>
      <b-tab title="Запит 2" >
        <br> Спортсмени за видом спорту
        <div class="row">
          <div class="col-md-3">
            <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                           v-model="sportKindId"
                           placeholder="Вид спорту">
              <option disabled slot="first" :value="null">Види спорту</option>
              <option v-for="sportKind in sportKinds" :value="sportKind.id">
                {{ sportKind.name }}
              </option>
            </b-form-select>
          </div>
          <div class="col-md-1">
            <button class="btn btn-info" @click="getSportsmenBySportKindId(sportKindId)">Пошук</button>
          </div>
        </div>
      </b-tab>
      <b-tab title="Запит 3">
        <br> Спортсмени за спортивним клубом
        <div class="row">
          <div class="col-md-3">
            <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                           v-model="sportClubId"
                           placeholder="Вид спорту">
              <option disabled slot="first" :value="null">Клуби</option>
              <option v-for="sportClub in sportClubs" :value="sportClub.sportClubId">
                {{ sportClub.name }}
              </option>
            </b-form-select>
          </div>
          <div class="col-md-1">
            <button class="btn btn-info" @click="getSportsmenBySportClubId(sportClubId)">Пошук</button>
          </div>
        </div>
      </b-tab>
      <b-tab title="Запит 4">
        <br>
        <div class="row">
          <div class="col-md-4">
            <button class="btn btn-success" @click="getOrderSportsmen">
              Сортувати за датою народження
            </button>
          </div>
        </div>
      </b-tab>
      <b-tab title="Запит 5">
        <br>Спортсмени які не брали участь в змаганнях в заданому діапазоні (по стартовій даті)
        <div class="row">
          <div class="col-md-2">
            <input type="date" v-model="firstDate" class="form-control">
          </div>
          <div class="col-md-2">
            <input type="date" v-model="secondDate" class="form-control">
          </div>
          <div class="col-md-2">
            <button @click="getSportsmenNotBetweenDate" class="btn btn-info">
              Пошук
            </button>
          </div>
        </div>
      </b-tab>
      <b-tab title="Усі" @click="fetchSportsmen" active>

      </b-tab>
    </b-tabs>
    <br>


    <!-- Modal -->
    <div v-if="showModal">
      <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-dialog">
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
                    <label class="my-1 mr-2">Клуб</label>
                    <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportClub">
                      <option v-for="sportClub in sportClubs" :value="sportClub">
                        {{sportClub.name}}
                      </option>
                    </select>
                  </div>
                  <button class="btn btn-warning" @click="showModal=false">Скасувати</button>
                  <button v-if="formAdd.lastName !== '' && formAdd.firstName !== '' &&
                                formAdd.middleName !== '' && formAdd.birthDate !== undefined &&
                                formAdd.sportClub !== undefined"
                          class="btn btn-success" @click="addSportsman">Зберегти</button>
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
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  Додати
                  <button type="button" class="close" @click="showUpdateModal=false">
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
                    <label class="my-1 mr-2">Клуб</label>
                    <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportClub">
                      <option v-for="sportClub in sportClubs" :value="sportClub">
                        {{sportClub.name}}
                      </option>
                    </select>
                  </div>

                  <div class="form-group">
                    <label class="my-1 mr-2">Види спорту</label>
                    <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportKinds" multiple>
                      <option v-for="sportKind in sportKinds" :value="sportKind">
                        {{sportKind.name}}
                      </option>
                    </select>
                  </div>
                  <button class="btn btn-warning" @click="showUpdateModal=false">Скасувати</button>
                  <button v-if="formAdd.lastName !== '' && formAdd.firstName !== '' &&
                                formAdd.middleName !== '' && formAdd.birthDate !== undefined &&
                                formAdd.sportClub !== undefined"
                          class="btn btn-success" @click="updateSportsman(formAdd)">Зберегти</button>
                  <button v-else class="btn btn-success" disabled>Зберегти</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
      </transition>
    </div>


    <b-card  v-for="sportsman in filteredList" :key="sportsman.sportsmanId" v-bind:data="sportsman" :title="sportsman.lastName + ' ' + sportsman.firstName"
             img-src="../static/img/sportsman.jpg"
             img-alt="Image"
             img-top
             tag="article"
             style="max-width: 20rem;"
             class="mb-2">
      <p class="card-text">
      <p>Дата народж. {{ sportsman.birthDate }}</p>
      <p>Клуб: {{sportsman.sportClub.name}}</p>
      <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                     :value="null"
                     id="inlineFormCustomSelectPref" placeholder="Види спорту">
        <option disabled slot="first" :value="null">Види спорту</option>
        <option v-for="sportKind in sportsman.sportKinds">
          {{sportKind.name}}
        </option>
      </b-form-select>
      </p>
      <b-button variant="success" @click="showUpdateModals(sportsman)">Змінити</b-button>
      <b-button @click="deleteSportsman(sportsman.sportsmanId)" variant="danger">Видалити</b-button>
    </b-card>
  </div>
</template>

<script>
  import axios from 'axios'
    export default {
      name: "Sportsman",
      data: () => ({
        search: '',
        sportsmen: [],
        sportClubs: [],
        sportClubId: {},
        sportKinds: [],
        sportKindId:{},
        coaches: [],
        coachId: {},
        showModal: false,
        showUpdateModal: false,
        formAdd: {},
        firstDate: {},
        secondDate: {},
        show: false,
      }),
      created() {
        this.fetchSportsmen();
        this.fetchSportClubs();
        this.fetchSportKinds();
        this.fetchCoaches();
      },
      methods: {
        fetchSportsmen() {
          axios.get('/api/sportsmen').then((response) => {
            this.sportsmen = response.data;
          })
        },
        fetchSportClubs() {
          axios.get('/api/sport-clubs').then((response) => {
            this.sportClubs = response.data;
          })
        },
        fetchSportKinds() {
          axios.get('/api/sport-kinds').then((response) => {
            this.sportKinds = response.data;
          })
        },
        fetchCoaches() {
          axios.get('/api/coaches').then((response) => {
            this.coaches = response.data;
          });
        },
        getSportsmenByCoachId(coachId) {
          axios.get('/api/sportsmen-by-coach/' + coachId).then((response) => {
            this.sportsmen = response.data;
          });
        },
        getSportsmenBySportKindId(sportKindId) {
          axios.get('/api/sportsmen-by-sport-kind/' + sportKindId).then((response) => {
            this.sportsmen = response.data;
          });
        },
        getSportsmenBySportClubId(sportClubId) {
          axios.get('/api/sportsmen-by-sport-club/' + sportClubId).then((response) => {
            this.sportsmen = response.data;
          });
        },
        getOrderSportsmen() {
          axios.get('/api/sportsmen-order').then((response) => {
            this.sportsmen = response.data;
          })
        },
        getSportsmenNotBetweenDate() {
          axios.get('/api/sportsmen/' + this.firstDate + '/' + this.secondDate).then((response) => {
              this.sportsmen = response.data;
            })
        },
        addSportsman() {
          this.showModal = false;
          axios.post('api/sportsmen/', this.formAdd).then(() => {
            this.fetchSportsmen();
          });
          this.formAdd = {};
        },
        deleteSportsman(id) {
          axios.delete('/api/sportsmen/' + id).then(() => {
            this.fetchSportsmen();
          });
        },
        updateSportsman(sportsman) {
          this.showUpdateModal = false;
          axios.put('api/sportsmen/' + sportsman.sportsmanId, sportsman).then(() => {
            fetchSportsmen();
          });
          this.formAdd = {};
        },
        showUpdateModals(sportsman) {
          this.showUpdateModal = true;
          this.formAdd = sportsman;
        }
      },
      computed: {
        filteredList() {
          return this.sportsmen.filter(sportsman => {
            return sportsman.lastName.toLowerCase().includes(this.search.toLowerCase())
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
