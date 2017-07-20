import {inject, TestBed} from "@angular/core/testing";
import {HttpModule, Response, ResponseOptions, XHRBackend} from "@angular/http";
import {MockBackend, MockConnection} from "@angular/http/testing";

import {ApiService} from "./api.service";

describe('ApiService', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [
        ApiService,
        {provide: XHRBackend, useClass: MockBackend}
      ]
    });
  });

  it('can list accounts', inject([ApiService, XHRBackend], (service: ApiService, mockBackend: MockBackend) => {

    const expectedAccounts = [
      {id: '1'},
      {id: '2'},
      {id: '3'},
    ];

    mockBackend.connections.subscribe((connection: MockConnection) => {
      if (connection.request.url === 'http://localhost:8080/accounts') {
        connection.mockRespond(new Response(new ResponseOptions({body: expectedAccounts})));
      } else {
        connection.mockRespond(new Response(new ResponseOptions({
          status: 404
        })));
      }
    });

    service.getAccounts()
      .subscribe((accounts: any[]) => {
        expect(accounts).toEqual(expectedAccounts);
      });
  }));
});
