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
      <button class="btn btn-outline-success" @click="fetchCompetitions">Усі</button>
    </div>
  </div>
  <br>
  <b-tabs pills card>
    <b-tab title="Запит 8" active>
      <br>
      <p>Змагання, які проходили в заданий проміжок (по стартовій даті)</p>
      <div class="row">
        <div class="col-md-2">
          <input type="date" v-model="firstDate" class="form-control">
        </div>
        <div class="col-md-2">
          <input type="date" v-model="secondDate" class="form-control">
        </div>
        <div class="col-md-2">
          <button class="btn btn-success" @click="getCompetitionsByDates(firstDate, secondDate)">
            Пошук
          </button>
        </div>
      </div>
    </b-tab>
    <b-tab title="Запит 9" >
      <br>
      <p>Змагання за організатором</p>
      <div class="row">
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="organizerId"
                         placeholder="Організатори">
            <option disabled slot="first" :value="null">Організатори</option>
            <option v-for="organizer in organizers" :value="organizer.organizerId">
              {{ organizer.lastName}} {{organizer.firstName}}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-1">
          <button class="btn btn-info" @click="getCompetitionByOrganizerId(organizerId)">Пошук</button>
        </div>
      </div>
    </b-tab>
    <b-tab title="Запит 10" >
      <br>
      <p>Змагання за видом спорту</p>
      <div class="row">
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="sportKindId"
                         placeholder="Види спорту">
            <option disabled slot="first" :value="null">Види спорту</option>
            <option v-for="sportKind in sportKinds" :value="sportKind.id">
              {{ sportKind.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-1">
          <button class="btn btn-info" @click="getCompetitionBySportKindId(sportKindId)">Пошук</button>
        </div>
      </div>
    </b-tab>
    <b-tab title="Запит 11" >
      <br>
      <p>Перелік змагань за видом спорту і спортзалом</p>
      <div class="row">
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="sportKindId1"
                         placeholder="Види спорту">
            <option disabled slot="first" :value="null">Види спорту</option>
            <option v-for="sportKind in sportKinds" :value="sportKind.id">
              {{ sportKind.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="gymId"
                         placeholder="Спортзали">
            <option disabled slot="first" :value="null">Спортзали</option>
            <option v-for="gym in gyms" :value="gym.gymId">
              {{ gym.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-1">
          <button class="btn btn-info" @click="getCompetitionsBySportKindAndGym(sportKindId1, gymId)">Пошук</button>
        </div>
      </div>
    </b-tab>
    <b-tab title="Запит 12" >
      <br>
      <p>Перелік змагань за видом спорту і стадіоном</p>
      <div class="row">
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="sportKindId2"
                         placeholder="Види спорту">
            <option disabled slot="first" :value="null">Види спорту</option>
            <option v-for="sportKind in sportKinds" :value="sportKind.id">
              {{ sportKind.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="stadiumId"
                         placeholder="Спортзали">
            <option disabled slot="first" :value="null">Спортзали</option>
            <option v-for="stadium in stadiums" :value="stadium.stadiumId">
              {{ stadium.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-1">
          <button class="btn btn-info" @click="getCompetitionsBySportKindAndStadium(sportKindId2, stadiumId)">Пошук</button>
        </div>
      </div>
    </b-tab>
    <b-tab title="Запит 13" >
      <br>
      <p>Перелік змагань за видом спорту і тенісним кортом</p>
      <div class="row">
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="sportKindId3"
                         placeholder="Види спорту">
            <option disabled slot="first" :value="null">Види спорту</option>
            <option v-for="sportKind in sportKinds" :value="sportKind.id">
              {{ sportKind.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="courtId"
                         placeholder="Спортзали">
            <option disabled slot="first" :value="null">Тенісні корти</option>
            <option v-for="court in courts" :value="court.courtId">
              {{ court.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-1">
          <button class="btn btn-info" @click="getCompetitionsBySportKindAndCourt(sportKindId3, courtId)">Пошук</button>
        </div>
      </div>
    </b-tab>
    <b-tab title="Запит 14" >
      <br>
      <p>Перелік змагань за видом спорту і організатором</p>
      <div class="row">
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="sportKindId4"
                         placeholder="Види спорту">
            <option disabled slot="first" :value="null">Види спорту</option>
            <option v-for="sportKind in sportKinds" :value="sportKind.id">
              {{ sportKind.name }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-3">
          <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                         v-model="organizerId1"
                         placeholder="Організатори">
            <option disabled slot="first" :value="null">Тенісні корти</option>
            <option v-for="organizer in organizers" :value="organizer.organizerId">
              {{ organizer.lastName }} {{ organizer.firstName }}
            </option>
          </b-form-select>
        </div>
        <div class="col-md-1">
          <button class="btn btn-info" @click="getCompetitionsBySportKindAndOrganizer(sportKindId4, organizerId1)">Пошук</button>
        </div>
      </div>
    </b-tab>
    <b-tab title="Усі" @click="fetchCompetitions" active>
    </b-tab>
  </b-tabs>
  <br>


  <b-card  v-for="competition in filteredList" :key="competition.competitionId" v-bind:data="competition" :title="competition.name"
           img-src="../static/img/competition.jpg"
           img-alt="Image"
           img-top
           tag="article"
           style="max-width: 20rem;"
           class="mb-2">
    <p class="card-text">
    <p>Старт : {{ competition.startDate}}</p>
    <p>Фініш : {{ competition.finishDate}}</p>
    <p>Організатор: {{competition.organizer.lastName}} {{competition.organizer.firstName}}</p>
    <p>Вид спорту: {{competition.sportKind.name}} </p>
    <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                   :value="null"
                   id="inlineFormCustomSelectPref" placeholder="Види спорту">
      <option disabled slot="first" :value="null">Спортивні споруди</option>
      <option disabled>Спортзали</option>
      <option v-for="gym in competition.gyms">
        {{gym.name}}
      </option>
      <option disabled>Стадіони</option>
      <option v-for="stadium in competition.stadiums">
        {{stadium.name}}
      </option>
    <option disabled>Тенісні корти</option>
      <option v-for="court in competition.courts">
        {{court.name}}
      </option>
    </b-form-select>
    <p></p>
      <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
              :value="null"
              id="inlineFormCustomSelectPref" placeholder="Спортсмени">
        <option disabled slot="first" :value="null">Спортсмени</option> >
        <option v-for="sportsman in competition.sportsmen" :value="sportsman">
          {{sportsman.lastName}} {{sportsman.firstName}}
        </option>
      </b-form-select>
    </p>
    <b-button variant="success" @click="showUpdateModals(competition)">Змінити</b-button>
    <b-button @click="deleteCompetition(competition.competitionId)" variant="danger">Видалити</b-button>
  </b-card>

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
                  <label>Назва</label>
                  <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                </div>

                <div class="form-group">
                  <label>Старт</label>
                  <input v-model="formAdd.startDate" type="date" class="form-control">
                </div>

                <div class="form-group">
                  <label>Фініш</label>
                  <input v-model="formAdd.finishDate" type="date" class="form-control">
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Організатори</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.organizer">
                    <option v-for="organizer in organizers" :value="organizer">
                      {{organizer.lastName}} {{organizer.firstName}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Види спорту</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportKind">
                    <option v-for="sportKind in sportKinds" :value="sportKind">
                      {{sportKind.name}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Стадіони</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.stadiums" multiple>
                    <option v-for="stadium in stadiums" :value="stadium">
                      {{stadium.name}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Тенісні корти</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.courts" multiple>
                    <option v-for="court in courts" :value="court">
                      {{court.name}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Спортзали</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.gyms" multiple>
                    <option v-for="gym in gyms" :value="gym">
                      {{gym.name}}
                    </option>
                  </select>
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
                <button v-if="formAdd.name !== undefined && formAdd.startDate !== undefined &&
                              formAdd.finishDate !== undefined && formAdd.organizer !== undefined &&
                              formAdd.sportKind !== undefined"
                        class="btn btn-success" @click="addCompetition">Зберегти</button>
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
                  <label>Назва</label>
                  <input v-model="formAdd.name" type="text" class="form-control" placeholder="Введіть назву">
                </div>

                <div class="form-group">
                  <label>Старт</label>
                  <input v-model="formAdd.startDate" type="date" class="form-control">
                </div>

                <div class="form-group">
                  <label>Фініш</label>
                  <input v-model="formAdd.finishDate" type="date" class="form-control">
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Організатори</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.organizer">
                    <option v-for="organizer in organizers" :value="organizer">
                      {{organizer.lastName}} {{organizer.firstName}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Види спорту</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportKind">
                    <option v-for="sportKind in sportKinds" :value="sportKind">
                      {{sportKind.name}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Стадіони</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.stadiums" multiple>
                    <option v-for="stadium in stadiums" :value="stadium">
                      {{stadium.name}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Тенісні корти</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.courts" multiple>
                    <option v-for="court in courts" :value="court">
                      {{court.name}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Спортзали</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.gyms" multiple>
                    <option v-for="gym in gyms" :value="gym">
                      {{gym.name}}
                    </option>
                  </select>
                </div>

                <div class="form-group">
                  <label class="my-1 mr-2">Спортзали</label>
                  <select class="custom-select my-1 mr-sm-2" v-model="formAdd.sportsmen" multiple>
                    <option v-for="sportsman in sportsmen" :value="sportsman">
                      {{sportsman.lastName}} {{sportsman.firstName}}
                    </option>
                  </select>
                </div>

                <button class="btn btn-warning" @click="showUpdateModal=false">Скасувати</button>
                <button v-if="formAdd.name !== undefined && formAdd.startDate !== undefined &&
                              formAdd.finishDate !== undefined && formAdd.organizer !== undefined &&
                              formAdd.sportKind !== undefined"
                        class="btn btn-success" @click="updateCompetition(formAdd)">Зберегти</button>
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
        name: "Competition",
      data: () => ({
        search: '',
        competitions: [],
        organizers: [],
        organizerId: {},
        organizerId1: {},
        sportKinds: [],
        sportKindId: {},
        sportKindId1: {},
        sportKindId2: {},
        sportKindId3: {},
        sportKindId4: {},
        stadiums: [],
        stadiumId:[],
        courts: [],
        courtId: {},
        sportsmen: [],
        gyms: [],
        gymId: {},
        formAdd: {},
        showModal: false,
        showUpdateModal: false,
        firstDate: {},
        secondDate: {},
      }),
      created() {
          this.fetchCompetitions();
          this.fetchOrganizers();
          this.fetchSportKinds();
          this.fetchSportsmen();
          this.fetchStadiums();
          this.fetchCourts();
          this.fetchGyms();
      },
      methods: {
        fetchCompetitions() {
          axios.get('/api/competitions').then((response) => {
            this.competitions = response.data;
          })
        },
        fetchOrganizers() {
          axios.get('/api/organizers').then((response) => {
            this.organizers = response.data;
          })
        },
        fetchSportKinds() {
          axios.get('/api/sport-kinds').then((response) => {
            this.sportKinds = response.data;
          })
        },
        fetchStadiums() {
          axios.get('/api/stadiums').then((response) => {
            this.stadiums = response.data;
          })
        },
        fetchCourts() {
          axios.get('/api/courts').then((response) => {
            this.courts = response.data;
          })
        },
        fetchGyms() {
          axios.get('/api/gyms').then((response) => {
            this.gyms = response.data;
          })
        },
        fetchSportsmen() {
          axios.get('/api/sportsmen').then((response) => {
            this.sportsmen = response.data;
          })
        },
        addCompetition() {
          this.showModal = false;
          axios.post('/api/competitions/', this.formAdd).then(() => {
            this.fetchCompetitions();
          });
          this.formAdd = {};
        },
        updateCompetition(competition) {
          this.showUpdateModal = false;
          axios.put('/api/competitions/' + competition.competitionId, competition).then(() => {
            this.fetchCompetitions();
          });
          this.formAdd = {};
        },
        deleteCompetition(id) {
          axios.delete('/api/competitions/' + id).then(() => {
            this.fetchCompetitions();
          })
        },
        showUpdateModals(coach) {
          this.showUpdateModal = true;
          this.formAdd = coach;
        },
        getCompetitionsByDates(firstDate, secondDate) {
          axios.get('/api/competitions/' + firstDate + '/' + secondDate).then((response) => {
            this.competitions = response.data;
          });
        },
        getCompetitionByOrganizerId(organizerId) {
          axios.get('/api/competitions-by-organizer/' + organizerId).then((response) =>{
            this.competitions = response.data;
          });
        },
        getCompetitionBySportKindId(sportKindId) {
          axios.get('/api/competitions-by-sport-kind/' + sportKindId).then((response) =>{
            this.competitions = response.data;
          });
        },
        getCompetitionsBySportKindAndGym(sportKindId, gymId) {
          axios.get('/api/competitions-by-sport-kind-gym/' + sportKindId + '/' + gymId).then((response) =>{
            this.competitions = response.data;
          });
        },
        getCompetitionsBySportKindAndStadium(sportKindId, stadiumId) {
          axios.get('/api/competitions-by-sport-kind-stadium/' + sportKindId + '/' + stadiumId).then((response) =>{
            this.competitions = response.data;
          });
        },
        getCompetitionsBySportKindAndCourt(sportKindId, courtId) {
          axios.get('/api/competitions-by-sport-kind-court/' + sportKindId + '/' + courtId).then((response) =>{
            this.competitions = response.data;
          });
        },
        getCompetitionsBySportKindAndOrganizer(sportKindId, organizerId) {
          axios.get('/api/competitions-by-sport-kind-organizer/' + sportKindId + '/' + organizerId).then((response) =>{
            this.competitions = response.data;
          });
        }
      },
      computed: {
        filteredList() {
          return this.competitions.filter(competition => {
            return competition.name.toLowerCase().includes(this.search.toLowerCase())
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
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }
</style>
